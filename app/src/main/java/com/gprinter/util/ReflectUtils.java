/*     */ package com.gprinter.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectUtils
/*     */ {
/*     */   public static <T, E> E getFieldValue(T t, Class<T> clazz, String filedName)
/*     */   {
/*  23 */     if (t == null) {
/*  24 */       return null;
/*     */     }
/*     */     try {
/*  27 */       Field field = clazz.getDeclaredField(filedName);
/*  28 */       boolean isAccessable = field.isAccessible();
/*  29 */       field.setAccessible(true);
/*  30 */       field.setAccessible(isAccessable);
/*  31 */       return (E)field.get(t);
/*     */     } catch (IllegalArgumentException e) {
/*  33 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/*  35 */       e.printStackTrace();
/*     */     } catch (SecurityException e) {
/*  37 */       e.printStackTrace();
/*     */     } catch (NoSuchFieldException e) {
/*  39 */       e.printStackTrace();
/*     */     }
/*  41 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T, E> E getMethodValue(T t, Class<T> clazz, String methodName)
/*     */   {
/*  59 */     if (t == null) {
/*  60 */       return null;
/*     */     }
/*     */     try {
/*  63 */       Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
/*  64 */       return (E)method.invoke(t, new Object[0]);
/*     */     }
/*     */     catch (IllegalAccessException e) {
/*  67 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/*  69 */       e.printStackTrace();
/*     */     } catch (SecurityException e) {
/*  71 */       e.printStackTrace();
/*     */     } catch (NoSuchMethodException e) {
/*  73 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e) {
/*  75 */       e.printStackTrace();
/*     */     }
/*  77 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <S, D> D mappingFieldByField(S s, D d)
/*     */   {
/*  94 */     if ((s == null) || (d == null)) {
/*  95 */       return d;
/*     */     }
/*  97 */     Field[] sfields = s.getClass().getDeclaredFields();
/*  98 */     Field[] dfields = d.getClass().getDeclaredFields();
/*     */     try { Field[] arrayOfField1;
/* 100 */       int j = (arrayOfField1 = sfields).length; for (int i = 0; i < j; i++) { Field sfield = arrayOfField1[i];
/* 101 */         String sName = sfield.getName();
/* 102 */         Class sType = sfield.getType();
/* 103 */         sfield.setAccessible(true);
/* 104 */         boolean sisAccessable = sfield.isAccessible();
/* 105 */         Field[] arrayOfField2; int m = (arrayOfField2 = dfields).length; for (int k = 0; k < m; k++) { Field dfield = arrayOfField2[k];
/* 106 */           String dName = dfield.getName();
/* 107 */           Class dType = dfield.getType();
/* 108 */           if ((sName.equals(dName)) && (sType.toString().equals(dType.toString()))) {
/* 109 */             boolean disAccessable = dfield.isAccessible();
/* 110 */             dfield.setAccessible(true);
/* 111 */             dfield.set(d, sfield.get(s));
/* 112 */             dfield.setAccessible(disAccessable);
/* 113 */             break;
/*     */           }
/*     */         }
/* 116 */         sfield.setAccessible(sisAccessable);
/*     */       }
/*     */     } catch (IllegalAccessException e) {
/* 119 */       e.printStackTrace();
/*     */     } catch (SecurityException e) {
/* 121 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e1) {
/* 123 */       e1.printStackTrace();
/*     */     }
/* 125 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <S, D> D mappingFieldByMethod(S s, D d)
/*     */   {
/* 142 */     if ((s == null) || (d == null)) {
/* 143 */       return d;
/*     */     }
/* 145 */     Field[] sfields = s.getClass().getDeclaredFields();
/* 146 */     Field[] dfields = d.getClass().getDeclaredFields();
/* 147 */     Class scls = s.getClass();
/* 148 */     Class dcls = d.getClass();
/*     */     try { Field[] arrayOfField1;
/* 150 */       int j = (arrayOfField1 = sfields).length; for (int i = 0; i < j; i++) { Field sfield = arrayOfField1[i];
/* 151 */         String sName = sfield.getName();
/* 152 */         Class sType = sfield.getType();
/* 153 */         String sfieldName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
/* 154 */         Method sGetMethod = scls.getMethod("get" + sfieldName, new Class[0]);
/* 155 */         Object value = sGetMethod.invoke(s, new Object[0]);
/* 156 */         Field[] arrayOfField2; int m = (arrayOfField2 = dfields).length; for (int k = 0; k < m; k++) { Field dfield = arrayOfField2[k];
/* 157 */           String dName = dfield.getName();
/* 158 */           Class dType = dfield.getType();
/* 159 */           if ((dName.equals(sName)) && (sType.toString().equals(dType.toString()))) {
/* 160 */             Method dSetMethod = dcls.getMethod("set" + sfieldName, new Class[] { sType });
/* 161 */             dSetMethod.invoke(d, new Object[] { value });
/* 162 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (SecurityException e) {
/* 167 */       e.printStackTrace();
/*     */     } catch (NoSuchMethodException e) {
/* 169 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 171 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/* 173 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e1) {
/* 175 */       e1.printStackTrace();
/*     */     }
/* 177 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <S, D> D mappingFieldByMethodExcludeParamNames(S s, D d, String... excludeParamNames)
/*     */   {
/* 194 */     if ((s == null) || (d == null)) {
/* 195 */       return d;
/*     */     }
/* 197 */     Field[] sfields = s.getClass().getDeclaredFields();
/* 198 */     Field[] dfields = d.getClass().getDeclaredFields();
/* 199 */     Class scls = s.getClass();
/* 200 */     Class dcls = d.getClass();
/*     */     try { Field[] arrayOfField1;
/* 202 */       int j = (arrayOfField1 = sfields).length; for (int i = 0; i < j; i++) { Field sfield = arrayOfField1[i];
/* 203 */         String sName = sfield.getName();
/* 204 */         Class sType = sfield.getType();
/* 205 */         String sfieldName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
/* 206 */         Method sGetMethod = scls.getMethod("get" + sfieldName, new Class[0]);
/* 207 */         Object value = sGetMethod.invoke(s, new Object[0]);
/* 208 */         Field[] arrayOfField2; int m = (arrayOfField2 = dfields).length; for (int k = 0; k < m; k++) { Field dfield = arrayOfField2[k];
/* 209 */           String dName = dfield.getName();
/* 210 */           Class dType = dfield.getType();
/* 211 */           if ((dName.equals(sName)) && (sType.toString().equals(dType.toString())) && 
/* 212 */             (excludeParamNames != null)) { String[] arrayOfString;
/* 213 */             int i1 = (arrayOfString = excludeParamNames).length; for (int n = 0; n < i1; n++) { String excludeParamName = arrayOfString[n];
/* 214 */               if (!sName.equals(excludeParamName)) {
/* 215 */                 Method dSetMethod = dcls.getMethod("set" + sfieldName, new Class[] { sType });
/* 216 */                 dSetMethod.invoke(d, new Object[] { value });
/* 217 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SecurityException e) {
/* 225 */       e.printStackTrace();
/*     */     } catch (NoSuchMethodException e) {
/* 227 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 229 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/* 231 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e1) {
/* 233 */       e1.printStackTrace();
/*     */     }
/* 235 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <S, D> D mappingFieldByMethodIncludeParamNames(S s, D d, String... includeParamNames)
/*     */   {
/* 252 */     if ((s == null) || (d == null)) {
/* 253 */       return d;
/*     */     }
/* 255 */     Field[] sfields = s.getClass().getDeclaredFields();
/* 256 */     Field[] dfields = d.getClass().getDeclaredFields();
/* 257 */     Class scls = s.getClass();
/* 258 */     Class dcls = d.getClass();
/*     */     try { Field[] arrayOfField1;
/* 260 */       int j = (arrayOfField1 = sfields).length; for (int i = 0; i < j; i++) { Field sfield = arrayOfField1[i];
/* 261 */         String sName = sfield.getName();
/* 262 */         Class sType = sfield.getType();
/* 263 */         String sfieldName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
/* 264 */         Method sGetMethod = scls.getMethod("get" + sfieldName, new Class[0]);
/* 265 */         Object value = sGetMethod.invoke(s, new Object[0]);
/* 266 */         if (includeParamNames != null) { String[] arrayOfString;
/* 267 */           int m = (arrayOfString = includeParamNames).length; for (int k = 0; k < m; k++) { String excludeParam = arrayOfString[k];
/* 268 */             if (sName.equals(excludeParam)) { Field[] arrayOfField2;
/* 269 */               int i1 = (arrayOfField2 = dfields).length; for (int n = 0; n < i1; n++) { Field dfield = arrayOfField2[n];
/* 270 */                 String dName = dfield.getName();
/* 271 */                 Class dType = dfield.getType();
/* 272 */                 if ((dName.equals(sName)) && (sType.toString().equals(dType.toString()))) {
/* 273 */                   Method dSetMethod = dcls.getMethod("set" + sfieldName, new Class[] { sType });
/* 274 */                   dSetMethod.invoke(d, new Object[] { value });
/* 275 */                   break;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (SecurityException e) {
/* 283 */       e.printStackTrace();
/*     */     } catch (NoSuchMethodException e) {
/* 285 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 287 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/* 289 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e1) {
/* 291 */       e1.printStackTrace();
/*     */     }
/* 293 */     return d;
/*     */   }
/*     */ }



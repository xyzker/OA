package oa.util;
/**
 * 字符串工具类
 */
public class StringUtil {
	/*
	 * 将字符串转换为数组
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null ;
	}

	/*
	 * 判断字符串数组中是否有某字符串	
	 */
	public static boolean contains(String[] values, String value) {
		if(ValidateUtil.isValid(values)){
			for(String s : values){
				if(s.equals(value)){
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * 将数组变成字符串，使用“,”分隔
	 */
	public static String arr2Str(Object[] arr){
		String tmp = "";
		if(ValidateUtil.isValid(arr)){
			for(Object s : arr){
				tmp = tmp + s + ",";
			}
			return tmp.substring(0, tmp.length() - 1);
		}
		return null;
	}
	
}

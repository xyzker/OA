package oa.util;
/**
 * �ַ���������
 */
public class StringUtil {
	/*
	 * ���ַ���ת��Ϊ����
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null ;
	}

	/*
	 * �ж��ַ����������Ƿ���ĳ�ַ���	
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
	 * ���������ַ�����ʹ�á�,���ָ�
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

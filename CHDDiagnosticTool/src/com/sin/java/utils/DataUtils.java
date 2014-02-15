package com.sin.java.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 数据工具类
 * 
 * @author RobinTang
 * @time 2012-10-13
 */
public class DataUtils {
	public static void writeByteArray(byte[] bytes, String filename) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filename);
		outputStream.write(bytes);
		outputStream.close();
	}

	public static byte[] readByteArray(String filename) throws IOException {
		File file = new File(filename);
		int len = (int) file.length();
		byte[] res = new byte[len];
		FileInputStream inputStream = new FileInputStream(filename);
		inputStream.read(res);
		inputStream.close();
		return res;
	}

	public static boolean writeIntArray(int[] data, String filename) {
		try {
			byte[] bytes = new byte[data.length * 4];
			for (int i = 0; i < data.length; ++i) {
				int2Bytes(data[i], bytes, i * 4);
			}
			writeByteArray(bytes, filename);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int[] readIntArray(String filename) {
		try {
			byte[] bytes = readByteArray(filename);
			int len = bytes.length / 4;
			int[] res = new int[len];
			for (int i = 0; i < len; ++i) {
				res[i] = bytes2Int(bytes, i * 4);
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int bytes2Int(byte[] bytes) {
		return bytes2Int(bytes, 0);
	}

	public static int bytes2Int(byte[] bytes, int offset) {
		int res = 0;
		switch (bytes.length) {
		case 1:
			res = bytes[0];
			break;
		case 2:
			res = (bytes[0] & 0x000000FF) | (((int) bytes[1]) << 8);
			break;
		case 3:
			res = (bytes[0] & 0x000000FF) | (((bytes[1] & 0x000000FF)) << 8) | (((int) bytes[1]) << 16);
			break;
		case 4:
			res = (bytes[0] & 0x000000FF) | (((bytes[1] & 0x000000FF)) << 8) | (((bytes[2] & 0x000000FF)) << 16) | (((int) bytes[3]) << 24);
			break;
		default:
			res = (bytes[offset] & 0x000000FF) | (((bytes[offset + 1] & 0x000000FF)) << 8) | (((bytes[offset + 2] & 0x000000FF)) << 16) | (((int) bytes[offset + 3]) << 24);
			break;
		}
		return res;
	}

	public static byte[] int2Bytes(int data) {
		return int2Bytes(data, null, 0);
	}

	public static byte[] int2Bytes(int data, byte[] buf, int offset) {
		byte[] res = new byte[4];
		res[3] = (byte) (data >>> 24);
		res[2] = (byte) (data >> 16);
		res[1] = (byte) (data >> 8);
		res[0] = (byte) (data & 0xFF);
		if (buf != null) {
			System.arraycopy(res, 0, buf, offset, 4);
			return buf;
		} else {
			return res;
		}
	}

	public static String readString(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String l = null;
			StringBuilder sb = new StringBuilder();
			while ((l = reader.readLine()) != null) {
				sb.append(l);
				sb.append("\n");
			}
			reader.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean writeString(String filename, String content) {
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(content);
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

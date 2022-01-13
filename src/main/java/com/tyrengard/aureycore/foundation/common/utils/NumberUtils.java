package com.tyrengard.aureycore.foundation.common.utils;

public class NumberUtils {
	public static int getRandomInt(int from, int to) {
		return (int)(Math.random() * to + from);
	}
	
	public static double roundToTwoDecimalPlaces(double d) {
		return (double) Math.round(d * 100) / 100;
	}
}

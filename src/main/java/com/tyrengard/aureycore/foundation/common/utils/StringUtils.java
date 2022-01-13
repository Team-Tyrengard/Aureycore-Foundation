package com.tyrengard.aureycore.foundation.common.utils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.ChatColor;

public class StringUtils {
	private final static TreeMap<Integer, String> romanNumeralMap = new TreeMap<>();
	public static int MAX_CHAT_SIZE = 130;

    static {
    	romanNumeralMap.put(1000, "M");
    	romanNumeralMap.put(900, "CM");
    	romanNumeralMap.put(500, "D");
    	romanNumeralMap.put(400, "CD");
        romanNumeralMap.put(100, "C");
        romanNumeralMap.put(90, "XC");
        romanNumeralMap.put(50, "L");
        romanNumeralMap.put(40, "XL");
        romanNumeralMap.put(10, "X");
        romanNumeralMap.put(9, "IX");
        romanNumeralMap.put(5, "V");
        romanNumeralMap.put(4, "IV");
        romanNumeralMap.put(1, "I");
    }

    public static String convertIntToRomanNumerals(int number) {
        int l =  romanNumeralMap.floorKey(number);
        if ( number == l ) {
            return romanNumeralMap.get(number);
        }
        return romanNumeralMap.get(l) + convertIntToRomanNumerals(number-l);
    }

    public static String stripNonAlphabeticCharacters(String input) {
        return input.replaceAll("\\W", "");
    }

    public static String toKeyCase(String input) {
        return ChatColor.stripColor(input).toUpperCase().replace(' ', '_');
    }

    public static String toKebabCase(String input) {
        return ChatColor.stripColor(input).toLowerCase().replace(' ', '-');
    }
    
	public static String toTitleCase(String input) {
		return String.join(" ", Arrays.stream(ChatColor.stripColor(input).split("[ _]"))
				.map(word -> String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1).toLowerCase())
				.toArray(String[]::new));
	}
    
	public static String padString(String str, char ch, int messageSize, StringPaddingOptions options) {
        int toCompensate = messageSize - getStringPxSize(str);
        int spacerLength = DefaultFontInfo.getDefaultFontInfo(ch).length;
        int compensated = 0;
        
		StringBuilder sb = new StringBuilder();
		switch (options) {
		case LEFT:
	        while(compensated < toCompensate) {
	            sb.append(ch);
	            compensated += spacerLength;
	        }
	        return ChatColor.translateAlternateColorCodes('&', str) + sb;
		case RIGHT:
	        while(compensated < toCompensate) {
	            sb.append(ch);
	            compensated += spacerLength;
	        }
	        return sb + ChatColor.translateAlternateColorCodes('&', str);
		case CENTER:
			toCompensate += getStringPxSize(str) / 2;
	        while(compensated < toCompensate) {
	            sb.append(ch);
	            compensated += spacerLength;
	        }
	        return sb + ChatColor.translateAlternateColorCodes('&', str) + sb;
        default: return str;
		}
	}
	
	public static List<String> breakIntoLines(String str, int length) {
		ArrayList<String> out = new ArrayList<>();
		BreakIterator bi = BreakIterator.getLineInstance();
		bi.setText(str);
		
		int start = bi.first();
	    int end = bi.next();
	    int lineLength = 0;
	    String currentLine = "";

	    while (end != BreakIterator.DONE) {
	        String word = str.substring(start,end);
	        int wordLength = 0;
	        for (char c : ChatColor.stripColor(word).toCharArray())
	        	wordLength += DefaultFontInfo.getDefaultFontInfo(c).length;
	        lineLength = lineLength + word.length();
	        if (lineLength + wordLength >= length) {
	        	out.add(currentLine);
	        	currentLine = "";
	            lineLength = wordLength;
	        } else lineLength += wordLength;
	        currentLine += word;
	        end = bi.next();
	    }
	    
	    return out;
	}
	
	private final static int CENTER_PX = 154;

	public static String centerAligned(String message){
        if(message == null || message.equals("")) 
        	return message;
       
        int halvedMessageSize = getStringPxSize(message) / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.length + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + ChatColor.translateAlternateColorCodes('&', message);
    }
	
	public static int getStringPxSize(String message) {
		int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;
       
        for(char c : ChatColor.translateAlternateColorCodes('&', message).toCharArray()) {
            if(c == ChatColor.COLOR_CHAR) {
                previousCode = true;
                continue;
            } else if(previousCode == true) {
                previousCode = false;
                if(c == 'l' || c == 'L') {
                        isBold = true;
                        continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.length;
                messagePxSize++;
            }
        }
        
        return messagePxSize;
	}

	
	public static enum StringPaddingOptions {
		LEFT, RIGHT, CENTER
	}
	
	public enum DefaultFontInfo {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);
       
		final char character;
        final int length;
       
        DefaultFontInfo(char character, int length) {
            this.character = character;
            this.length = length;
        }
       
        public char getCharacter(){
            return this.character;
        }
       
        public int getBoldLength(){
            if(this == DefaultFontInfo.SPACE) return this.length;
            return this.length + 1;
        }
       
        public static DefaultFontInfo getDefaultFontInfo(char c) {
            for(DefaultFontInfo dFI : DefaultFontInfo.values()) {
                    if(dFI.getCharacter() == c) return dFI;
            }
            return DefaultFontInfo.DEFAULT;
        }
	}
}


package com.tuotuogroup.core.utils;

import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String add(String s, String add) {
		return add(s, add, StringPool.COMMA);
	}

	public static String add(String s, String add, String delimiter) {
		return add(s, add, delimiter, false);
	}

	public static String add(String s, String add, String delimiter, boolean allowDuplicates) {

		if ((add == null) || (delimiter == null)) {
			return null;
		}

		if (s == null) {
			s = StringPool.BLANK;
		}

		if (allowDuplicates || !contains(s, add, delimiter)) {
			StringBuffer sb = new StringBuffer();

			sb.append(s);

			if (Validator.isNull(s) || s.endsWith(delimiter)) {
				sb.append(add);
				sb.append(delimiter);
			} else {
				sb.append(delimiter);
				sb.append(add);
				sb.append(delimiter);
			}

			s = sb.toString();
		}

		return s;
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1);

			if (hex.length() < 2) {
				sb.append("0");
			}

			sb.append(hex);
		}

		return sb.toString();
	}

	public static boolean contains(String s, String text) {
		return contains(s, text, StringPool.COMMA);
	}

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s = s.concat(delimiter);
		}

		String dtd = delimiter.concat(text).concat(delimiter);

		int pos = s.indexOf(dtd);

		if (pos == -1) {
			String td = text.concat(delimiter);

			if (s.startsWith(td)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());

			count++;
		}

		return count;
	}

	public static boolean endsWith(String s, char end) {
		return endsWith(s, (new Character(end)).toString());
	}

	public static boolean endsWith(String s, String end) {
		if ((s == null) || (end == null)) {
			return false;
		}

		if (end.length() > s.length()) {
			return false;
		}

		String temp = s.substring(s.length() - end.length(), s.length());

		if (temp.equalsIgnoreCase(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static String extractChars(String s) {
		if (s == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = s.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (Validator.isChar(chars[i])) {
				sb.append(chars[i]);
			}
		}

		return sb.toString();
	}

	public static String extractDigits(String s) {
		if (s == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = s.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (Validator.isDigit(chars[i])) {
				sb.append(chars[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * @deprecated
	 */
	public static String highlight(String s, String keywords) {
		return highlight(s, keywords, "<span class=\"highlight\">", "</span>");
	}

	/**
	 * @deprecated
	 */
	public static String highlight(String s, String keywords, String highlight1, String highlight2) {

		if (Validator.isNull(s) || Validator.isNull(keywords)) {
			return s;
		}

		Pattern pattern = Pattern.compile(Pattern.quote(keywords), Pattern.CASE_INSENSITIVE);

		return _highlight(s, pattern, highlight1, highlight2);
	}

	public static String highlight(String s, String[] queryTerms) {
		return highlight(s, queryTerms, "<span class=\"highlight\">", "</span>");
	}

	public static String highlight(String s, String[] queryTerms, String highlight1, String highlight2) {

		if (Validator.isNull(s) || Validator.isNull(queryTerms)) {
			return s;
		}

		StringBuffer sb = null;

		if (queryTerms.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * queryTerms.length - 1);
		}

		for (int i = 0; i < queryTerms.length; i++) {
			sb.append(Pattern.quote(queryTerms[i].trim()));

			if ((i + 1) < queryTerms.length) {
				sb.append(StringPool.PIPE);
			}
		}

		int flags = Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

		Pattern pattern = Pattern.compile(sb.toString(), flags);

		return _highlight(s, pattern, highlight1, highlight2);
	}

	public static String insert(String s, String insert, int offset) {
		if (s == null) {
			return null;
		}

		if (insert == null) {
			return s;
		}

		if (offset > s.length()) {
			offset = s.length();
		}

		StringBuilder sb = new StringBuilder(s);

		sb.insert(offset, insert);

		return sb.toString();
	}

	public static String lowerCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toLowerCase();
		}
	}

	public static boolean matches(String s, String pattern) {
		String[] array = pattern.split("\\*");

		for (int i = 0; i < array.length; i++) {
			int pos = s.indexOf(array[i]);

			if (pos == -1) {
				return false;
			}

			s = s.substring(pos + array[i].length());
		}

		return true;
	}

	public static String merge(boolean[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(boolean[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(Collection<?> col) {
		return merge(col, StringPool.COMMA);
	}

	public static String merge(Collection<?> col, String delimiter) {
		if (col == null) {
			return null;
		}

		return merge(col.toArray(new Object[col.size()]), delimiter);
	}

	public static String merge(double[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(double[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(float[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(float[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(int[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(int[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(long[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(long[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(Object[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(Object[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(short[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(short[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = null;

		if (array.length == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * array.length - 1);
		}

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String remove(String s, String remove) {
		return remove(s, remove, StringPool.COMMA);
	}

	public static String remove(String s, String remove, String delimiter) {
		if ((s == null) || (remove == null) || (delimiter == null)) {
			return null;
		}

		if (Validator.isNotNull(s) && !s.endsWith(delimiter)) {
			s += delimiter;
		}

		String drd = delimiter.concat(remove).concat(delimiter);

		String rd = remove.concat(delimiter);

		while (contains(s, remove, delimiter)) {
			int pos = s.indexOf(drd);

			if (pos == -1) {
				if (s.startsWith(rd)) {
					int x = remove.length() + delimiter.length();
					int y = s.length();

					s = s.substring(x, y);
				}
			} else {
				int x = pos + remove.length() + delimiter.length();
				int y = s.length();

				String temp = s.substring(0, pos);

				s = temp.concat(s.substring(x, y));
			}
		}

		return s;
	}

	public static String replace(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return s.replace(oldSub, newSub);
	}

	public static String replace(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		// The number 5 is arbitrary and is used as extra padding to reduce
		// buffer expansion

		StringBuilder sb = new StringBuilder(s.length() + 5 * newSub.length());

		char[] chars = s.toCharArray();

		for (char c : chars) {
			if (c == oldSub) {
				sb.append(newSub);
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String replace(String s, String oldSub, String newSub) {
		return replace(s, oldSub, newSub, 0);
	}

	public static String replace(String s, String oldSub, String newSub, int fromIndex) {

		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(StringPool.BLANK)) {
			return s;
		}

		int y = s.indexOf(oldSub, fromIndex);

		if (y >= 0) {
			StringBuffer sb = new StringBuffer();

			int length = oldSub.length();
			int x = 0;

			while (x <= y) {
				sb.append(s.substring(x, y));
				sb.append(newSub);

				x = y + length;
				y = s.indexOf(oldSub, x);
			}

			sb.append(s.substring(x));

			return sb.toString();
		} else {
			return s;
		}
	}

	public static String replace(String s, String begin, String end, Map<String, String> values) {

		StringBuffer sb = replaceToStringBuffer(s, begin, end, values);

		return sb.toString();
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs, boolean exactMatch) {

		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		if (!exactMatch) {
			replace(s, oldSubs, newSubs);
		} else {
			for (int i = 0; i < oldSubs.length; i++) {
				s = s.replaceAll("\\b" + oldSubs[i] + "\\b", newSubs[i]);
			}
		}

		return s;
	}

	public static String replaceFirst(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return replaceFirst(s, String.valueOf(oldSub), String.valueOf(newSub));
	}

	public static String replaceFirst(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		return replaceFirst(s, String.valueOf(oldSub), newSub);
	}

	public static String replaceFirst(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(newSub)) {
			return s;
		}

		int y = s.indexOf(oldSub);

		if (y >= 0) {
			return s.substring(0, y).concat(newSub).concat(s.substring(y + oldSub.length()));
		} else {
			return s;
		}
	}

	public static String replaceFirst(String s, String[] oldSubs, String[] newSubs) {

		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replaceFirst(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String replaceLast(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return replaceLast(s, String.valueOf(oldSub), String.valueOf(newSub));
	}

	public static String replaceLast(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		return replaceLast(s, String.valueOf(oldSub), newSub);
	}

	public static String replaceLast(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(newSub)) {
			return s;
		}

		int y = s.lastIndexOf(oldSub);

		if (y >= 0) {
			return s.substring(0, y).concat(newSub).concat(s.substring(y + oldSub.length()));
		} else {
			return s;
		}
	}

	public static String replaceLast(String s, String[] oldSubs, String[] newSubs) {

		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replaceLast(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static StringBuffer replaceToStringBuffer(String s, String begin, String end, Map<String, String> values) {

		if ((s == null) || (begin == null) || (end == null) || (values == null) || (values.size() == 0)) {

			return new StringBuffer(s);
		}

		StringBuffer sb = new StringBuffer(values.size() * 2 + 1);

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos, s.length()));

				break;
			} else {
				sb.append(s.substring(pos, x));

				String oldValue = s.substring(x + begin.length(), y);

				String newValue = values.get(oldValue);

				if (newValue == null) {
					newValue = oldValue;
				}

				sb.append(newValue);

				pos = y + end.length();
			}
		}

		return sb;
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();
		char[] reverse = new char[chars.length];

		for (int i = 0; i < chars.length; i++) {
			reverse[i] = chars[chars.length - i - 1];
		}

		return new String(reverse);
	}

	public static String safePath(String path) {
		return replace(path, StringPool.DOUBLE_SLASH, StringPool.SLASH);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "...");
	}

	public static String shorten(String s, int length, String suffix) {
		if ((s == null) || (suffix == null)) {
			return null;
		}

		if (s.length() > length) {
			for (int j = length; j >= 0; j--) {
				if (Character.isWhitespace(s.charAt(j))) {
					length = j;

					break;
				}
			}

			String temp = s.substring(0, length);

			s = temp.concat(suffix);
		}

		return s;
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	public static boolean startsWith(String s, String start) {
		if ((s == null) || (start == null)) {
			return false;
		}

		if (start.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, start.length());

		if (temp.equalsIgnoreCase(start)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return the number of starting letters that s1 and s2 have in common
	 * before they deviate.
	 * 
	 * @return the number of starting letters that s1 and s2 have in common
	 *         before they deviate
	 */
	public static int startsWithWeight(String s1, String s2) {
		if ((s1 == null) || (s2 == null)) {
			return 0;
		}

		char[] chars1 = s1.toCharArray();
		char[] chars2 = s2.toCharArray();

		int i = 0;

		for (; (i < chars1.length) && (i < chars2.length); i++) {
			if (chars1[i] != chars2[i]) {
				break;
			}
		}

		return i;
	}

	public static String strip(String s, char remove) {
		if (s == null) {
			return null;
		}

		int x = s.indexOf(remove);

		if (x < 0) {
			return s;
		}

		int y = 0;

		StringBuilder sb = new StringBuilder(s.length());

		while (x >= 0) {
			sb.append(s.subSequence(y, x));

			y = x + 1;

			x = s.indexOf(remove, y);
		}

		sb.append(s.substring(y));

		return sb.toString();
	}

	public static String stripBetween(String s, String begin, String end) {
		if ((s == null) || (begin == null) || (end == null)) {
			return s;
		}

		StringBuilder sb = new StringBuilder(s.length());

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos, s.length()));

				break;
			} else {
				sb.append(s.substring(pos, x));

				pos = y + end.length();
			}
		}

		return sb.toString();
	}

	public static String toCharCode(String s) {
		StringBuffer sb = new StringBuffer(s.length());

		for (int i = 0; i < s.length(); i++) {
			sb.append(s.codePointAt(i));
		}

		return sb.toString();
	}

	public static String toHexString(int i) {
		char[] buffer = new char[8];

		int index = 8;

		do {
			buffer[--index] = _HEX_DIGITS[i & 15];

			i >>>= 4;
		} while (i != 0);

		return new String(buffer, index, 8 - index);
	}

	public static String toHexString(long l) {
		char[] buffer = new char[16];

		int index = 16;

		do {
			buffer[--index] = _HEX_DIGITS[(int) (l & 15)];

			l >>>= 4;
		} while (l != 0);

		return new String(buffer, index, 16 - index);
	}

	public static String toHexString(Object obj) {
		if (obj instanceof Integer) {
			return toHexString(((Integer) obj).intValue());
		} else if (obj instanceof Long) {
			return toHexString(((Long) obj).longValue());
		} else {
			return String.valueOf(obj);
		}
	}

	public static String trim(String s) {
		return trim(s, null);
	}

	public static String trim(String s, char c) {
		return trim(s, new char[] { c });
	}

	public static String trim(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		int len = chars.length;

		int x = 0;
		int y = chars.length;

		for (int i = 0; i < len; i++) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				x = i + 1;
			} else {
				break;
			}
		}

		for (int i = len - 1; i >= 0; i--) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				y = i;
			} else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		} else {
			return s;
		}
	}

	public static String trimLeading(String s) {
		return trimLeading(s, null);
	}

	public static String trimLeading(String s, char c) {
		return trimLeading(s, new char[] { c });
	}

	public static String trimLeading(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		int len = chars.length;

		int x = 0;
		int y = chars.length;

		for (int i = 0; i < len; i++) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				x = i + 1;
			} else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		} else {
			return s;
		}
	}

	public static String trimTrailing(String s) {
		return trimTrailing(s, null);
	}

	public static String trimTrailing(String s, char c) {
		return trimTrailing(s, new char[] { c });
	}

	public static String trimTrailing(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		int len = chars.length;

		int x = 0;
		int y = chars.length;

		for (int i = len - 1; i >= 0; i--) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				y = i;
			} else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		} else {
			return s;
		}
	}

	public static String unquote(String s) {
		if (Validator.isNull(s)) {
			return s;
		}

		if ((s.charAt(0) == CharPool.APOSTROPHE) && (s.charAt(s.length() - 1) == CharPool.APOSTROPHE)) {

			return s.substring(1, s.length() - 1);
		} else if ((s.charAt(0) == CharPool.QUOTE) && (s.charAt(s.length() - 1) == CharPool.QUOTE)) {

			return s.substring(1, s.length() - 1);
		}

		return s;
	}

	public static String upperCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toUpperCase();
		}
	}

	public static String upperCaseFirstLetter(String s) {
		char[] chars = s.toCharArray();

		if ((chars[0] >= 97) && (chars[0] <= 122)) {
			chars[0] = (char) (chars[0] - 32);
		}

		return new String(chars);
	}

	public static String valueOf(Object obj) {
		return String.valueOf(obj);
	}

	private static String _highlight(String s, Pattern pattern, String highlight1, String highlight2) {

		StringTokenizer st = new StringTokenizer(s);

		StringBuffer sb = null;

		if (st.countTokens() == 0) {
			sb = new StringBuffer();
		} else {
			sb = new StringBuffer(2 * st.countTokens() - 1);
		}

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			Matcher matcher = pattern.matcher(token);

			if (matcher.find()) {
				StringBuffer hightlighted = new StringBuffer();

				do {
					matcher.appendReplacement(hightlighted, highlight1 + matcher.group() + highlight2);
				} while (matcher.find());

				matcher.appendTail(hightlighted);

				sb.append(hightlighted);
			} else {
				sb.append(token);
			}

			if (st.hasMoreTokens()) {
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private static boolean _isTrimable(char c, char[] exceptions) {
		if ((exceptions != null) && (exceptions.length > 0)) {
			for (int i = 0; i < exceptions.length; i++) {
				if (c == exceptions[i]) {
					return false;
				}
			}
		}

		return Character.isWhitespace(c);
	}

	private static final char[] _HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public static String[] split(String s, String delimiter) {
		if ((Validator.isNull(s)) || (delimiter == null) || (delimiter.equals(StringPool.BLANK))) {

			return new String[0];
		}

		s = s.trim();

		if (s.equals(delimiter)) {
			return new String[0];
		}

		return s.split(delimiter);
	}

}
package cn.hutool.core.text;

import cn.hutool.core.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class CharSequenceUtilTest {

	@Test
	public void replaceTest() {
		String actual = CharSequenceUtil.replace("SSM15930297701BeryAllen", Pattern.compile("[0-9]"), matcher -> "");
		Assert.assertEquals("SSMBeryAllen", actual);
	}

	@Test
	public void replaceTest2() {
		// https://gitee.com/dromara/hutool/issues/I4M16G
		String replace = "#{A}";
		String result = CharSequenceUtil.replace(replace, "#{AAAAAAA}", "1");
		Assert.assertEquals(replace, result);
	}

	@Test
	public void replaceByStrTest() {
		String replace = "SSM15930297701BeryAllen";
		String result = CharSequenceUtil.replaceByCodePoint(replace, 5, 12, "***");
		Assert.assertEquals("SSM15***01BeryAllen", result);
	}

	@Test
	public void addPrefixIfNotTest() {
		String str = "hutool";
		String result = CharSequenceUtil.addPrefixIfNot(str, "hu");
		Assert.assertEquals(str, result);

		result = CharSequenceUtil.addPrefixIfNot(str, "Good");
		Assert.assertEquals("Good" + str, result);
	}

	@Test
	public void addSuffixIfNotTest() {
		String str = "hutool";
		String result = CharSequenceUtil.addSuffixIfNot(str, "tool");
		Assert.assertEquals(str, result);

		result = CharSequenceUtil.addSuffixIfNot(str, " is Good");
		Assert.assertEquals(str + " is Good", result);

		// https://gitee.com/dromara/hutool/issues/I4NS0F
		result = CharSequenceUtil.addSuffixIfNot("", "/");
		Assert.assertEquals("/", result);
	}

	@Test
	public void normalizeTest() {
		// https://blog.csdn.net/oscar999/article/details/105326270

		String str1 = "\u00C1";
		String str2 = "\u0041\u0301";

		Assert.assertNotEquals(str1, str2);

		str1 = CharSequenceUtil.normalize(str1);
		str2 = CharSequenceUtil.normalize(str2);
		Assert.assertEquals(str1, str2);
	}

	@Test
	public void indexOfTest() {
		int index = CharSequenceUtil.indexOf("abc123", '1');
		Assert.assertEquals(3, index);
		index = CharSequenceUtil.indexOf("abc123", '3');
		Assert.assertEquals(5, index);
		index = CharSequenceUtil.indexOf("abc123", 'a');
		Assert.assertEquals(0, index);
	}

	@Test
	public void indexOfTest2() {
		int index = CharSequenceUtil.indexOf("abc123", '1', 0, 3);
		Assert.assertEquals(-1, index);

		index = CharSequenceUtil.indexOf("abc123", 'b', 0, 3);
		Assert.assertEquals(1, index);
	}

	@Test
	public void subPreGbkTest() {
		// https://gitee.com/dromara/hutool/issues/I4JO2E
		String s = "华硕K42Intel酷睿i31代2G以下独立显卡不含机械硬盘固态硬盘120GB-192GB4GB-6GB";

		String v = CharSequenceUtil.subPreGbk(s, 40, false);
		Assert.assertEquals(39, v.getBytes(CharsetUtil.CHARSET_GBK).length);

		v = CharSequenceUtil.subPreGbk(s, 40, true);
		Assert.assertEquals(41, v.getBytes(CharsetUtil.CHARSET_GBK).length);
	}

	@Test
	public void startWithTest() {
		// https://gitee.com/dromara/hutool/issues/I4MV7Q
		Assert.assertFalse(CharSequenceUtil.startWith("123", "123", false, true));
		Assert.assertFalse(CharSequenceUtil.startWith(null, null, false, true));
		Assert.assertFalse(CharSequenceUtil.startWith("abc", "abc", true, true));

		Assert.assertTrue(CharSequenceUtil.startWithIgnoreCase(null, null));
		Assert.assertFalse(CharSequenceUtil.startWithIgnoreCase(null, "abc"));
		Assert.assertFalse(CharSequenceUtil.startWithIgnoreCase("abcdef", null));
		Assert.assertTrue(CharSequenceUtil.startWithIgnoreCase("abcdef", "abc"));
		Assert.assertTrue(CharSequenceUtil.startWithIgnoreCase("ABCDEF", "abc"));
	}

	@Test
	public void endWithTest() {
		Assert.assertFalse(CharSequenceUtil.endWith("123", "123", false, true));
		Assert.assertFalse(CharSequenceUtil.endWith(null, null, false, true));
		Assert.assertFalse(CharSequenceUtil.endWith("abc", "abc", true, true));

		Assert.assertTrue(CharSequenceUtil.endWithIgnoreCase(null, null));
		Assert.assertFalse(CharSequenceUtil.endWithIgnoreCase(null, "abc"));
		Assert.assertFalse(CharSequenceUtil.endWithIgnoreCase("abcdef", null));
		Assert.assertTrue(CharSequenceUtil.endWithIgnoreCase("abcdef", "def"));
		Assert.assertTrue(CharSequenceUtil.endWithIgnoreCase("ABCDEF", "def"));
	}

	@Test
	public void removePrefixIgnoreCaseTest() {
		Assert.assertEquals("de", CharSequenceUtil.removePrefixIgnoreCase("ABCde", "abc"));
		Assert.assertEquals("de", CharSequenceUtil.removePrefixIgnoreCase("ABCde", "ABC"));
		Assert.assertEquals("de", CharSequenceUtil.removePrefixIgnoreCase("ABCde", "Abc"));
		Assert.assertEquals("ABCde", CharSequenceUtil.removePrefixIgnoreCase("ABCde", ""));
		Assert.assertEquals("ABCde", CharSequenceUtil.removePrefixIgnoreCase("ABCde", null));
		Assert.assertEquals("", CharSequenceUtil.removePrefixIgnoreCase("ABCde", "ABCde"));
		Assert.assertEquals("ABCde", CharSequenceUtil.removePrefixIgnoreCase("ABCde", "ABCdef"));
		Assert.assertNull(CharSequenceUtil.removePrefixIgnoreCase(null, "ABCdef"));
	}

	@Test
	public void removeSuffixIgnoreCaseTest() {
		Assert.assertEquals("AB", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", "cde"));
		Assert.assertEquals("AB", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", "CDE"));
		Assert.assertEquals("AB", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", "Cde"));
		Assert.assertEquals("ABCde", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", ""));
		Assert.assertEquals("ABCde", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", null));
		Assert.assertEquals("", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", "ABCde"));
		Assert.assertEquals("ABCde", CharSequenceUtil.removeSuffixIgnoreCase("ABCde", "ABCdef"));
		Assert.assertNull(CharSequenceUtil.removeSuffixIgnoreCase(null, "ABCdef"));
	}

	/**
	 * 由于被测试类的方法众多，建议单元测试里面的顺序，也按照被测试类来写。这样方便查找和阅读。
	 */
	@Test
	public void stripTest() {

		final String SOURCE_STRING = "aaa_STRIPPED_bbb";

		// ---------------------------- test strip ----------------------------

		// Normal test
		Assert.assertEquals("aa_STRIPPED_bbb", CharSequenceUtil.strip(SOURCE_STRING, "a"));
		Assert.assertEquals(SOURCE_STRING, CharSequenceUtil.strip(SOURCE_STRING, ""));
		Assert.assertEquals("aa_STRIPPED_bb", CharSequenceUtil.strip(SOURCE_STRING, "a", "b"));

		// test null param
		Assert.assertEquals(SOURCE_STRING, CharSequenceUtil.strip(SOURCE_STRING, null, null));
		Assert.assertEquals(SOURCE_STRING, CharSequenceUtil.strip(SOURCE_STRING, "", ""));
		Assert.assertEquals("aaa_STRIPPED_bb", CharSequenceUtil.strip(SOURCE_STRING, "", "b"));
		Assert.assertEquals("aaa_STRIPPED_bb", CharSequenceUtil.strip(SOURCE_STRING, null, "b"));
		Assert.assertEquals("aa_STRIPPED_bbb", CharSequenceUtil.strip(SOURCE_STRING, "a", ""));
		Assert.assertEquals("aa_STRIPPED_bbb", CharSequenceUtil.strip(SOURCE_STRING, "a", null));
		// 本次提交前无法通过的 case
		Assert.assertEquals("", CharSequenceUtil.strip("a", "a", "a"));

		// ---------------------------- test stripAll ----------------------------

		// Normal test
		Assert.assertEquals("_STRIPPED_bbb", CharSequenceUtil.stripAll(SOURCE_STRING, "a"));
		Assert.assertEquals(SOURCE_STRING, CharSequenceUtil.stripAll(SOURCE_STRING, ""));

		// test null param
		Assert.assertEquals("_STRIPPED_", CharSequenceUtil.stripAll(SOURCE_STRING, "a", "b"));
		Assert.assertEquals(SOURCE_STRING, CharSequenceUtil.stripAll(SOURCE_STRING, null, null));
		Assert.assertEquals(SOURCE_STRING, CharSequenceUtil.stripAll(SOURCE_STRING, "", ""));
		Assert.assertEquals("aaa_STRIPPED_", CharSequenceUtil.stripAll(SOURCE_STRING, "", "b"));
		Assert.assertEquals("aaa_STRIPPED_", CharSequenceUtil.stripAll(SOURCE_STRING, null, "b"));
		Assert.assertEquals("_STRIPPED_bbb", CharSequenceUtil.stripAll(SOURCE_STRING, "a", ""));
		Assert.assertEquals("_STRIPPED_bbb", CharSequenceUtil.stripAll(SOURCE_STRING, "a", null));

		// special test
		Assert.assertEquals("bbb", CharSequenceUtil.stripAll("aaaaaabbb", "aaa", null));
		Assert.assertEquals("abbb", CharSequenceUtil.stripAll("aaaaaaabbb", "aa", null));

		// aaaaaaaaa (9个a) 可以被看为 aaa_aaaa_aa
		Assert.assertEquals("", CharSequenceUtil.stripAll("aaaaaaaaa", "aaa", "aa"));
		// 第二次迭代后会出现 from 比 to 大的情况，原本代码是强行交换，但是回导致无法去除前后缀
		Assert.assertEquals("", CharSequenceUtil.stripAll("a", "a", "a"));

	}


	@Test
	public void trimToNullTest() {
		String a = "  ";
		Assert.assertNull(CharSequenceUtil.trimToNull(a));

		a = "";
		Assert.assertNull(CharSequenceUtil.trimToNull(a));

		a = null;
		Assert.assertNull(CharSequenceUtil.trimToNull(a));
	}

	@Test
	public void commonPrefixTest() throws Exception {

		// -------------------------- None match -----------------------

		Assert.assertEquals("", CharSequenceUtil.commonPrefix("", "abc"));
		Assert.assertEquals("", CharSequenceUtil.commonPrefix(null, "abc"));
		Assert.assertEquals("", CharSequenceUtil.commonPrefix("abc", null));
		Assert.assertEquals("", CharSequenceUtil.commonPrefix("abc", ""));

		Assert.assertEquals("", CharSequenceUtil.commonPrefix("azzzj", "bzzzj"));

		Assert.assertEquals("", CharSequenceUtil.commonPrefix("english中文", "french中文"));

		// -------------------------- Matched -----------------------

		Assert.assertEquals("name_", CharSequenceUtil.commonPrefix("name_abc", "name_efg"));

		Assert.assertEquals("zzzj", CharSequenceUtil.commonPrefix("zzzja", "zzzjb"));

		Assert.assertEquals("中文", CharSequenceUtil.commonPrefix("中文english", "中文french"));

		// { space * 10 } + "abc"
		final String str1 = CharSequenceUtil.repeat(CharSequenceUtil.SPACE, 10) + "abc";

		// { space * 5 } + "efg"
		final String str2 = CharSequenceUtil.repeat(CharSequenceUtil.SPACE, 5) + "efg";

		// Expect common prefix: { space * 5 }
		Assert.assertEquals(CharSequenceUtil.repeat(CharSequenceUtil.SPACE, 5), CharSequenceUtil.commonPrefix(str1, str2));
	}

	@Test
	public void commonSuffixTest() throws Exception {

		// -------------------------- None match -----------------------

		Assert.assertEquals("", CharSequenceUtil.commonSuffix("", "abc"));
		Assert.assertEquals("", CharSequenceUtil.commonSuffix(null, "abc"));
		Assert.assertEquals("", CharSequenceUtil.commonSuffix("abc", null));
		Assert.assertEquals("", CharSequenceUtil.commonSuffix("abc", ""));

		Assert.assertEquals("", CharSequenceUtil.commonSuffix("zzzja", "zzzjb"));

		Assert.assertEquals("", CharSequenceUtil.commonSuffix("中文english", "中文Korean"));

		// -------------------------- Matched -----------------------

		Assert.assertEquals("_name", CharSequenceUtil.commonSuffix("abc_name", "efg_name"));

		Assert.assertEquals("zzzj", CharSequenceUtil.commonSuffix("abczzzj", "efgzzzj"));

		Assert.assertEquals("中文", CharSequenceUtil.commonSuffix("english中文", "Korean中文"));

		// "abc" + { space * 10 }
		final String str1 = "abc" + CharSequenceUtil.repeat(CharSequenceUtil.SPACE, 10);

		// "efg" + { space * 15 }
		final String str2 = "efg" + CharSequenceUtil.repeat(CharSequenceUtil.SPACE, 15);

		// Expect common suffix: { space * 10 }
		Assert.assertEquals(CharSequenceUtil.repeat(CharSequenceUtil.SPACE, 10), CharSequenceUtil.commonSuffix(str1, str2));
	}

	@Test
	public void testContainsOnly() {
		// 测试空字符串
		Assert.assertTrue(CharSequenceUtil.containsOnly("", 'a', 'b'));

		// 测试字符串只包含testChars中的字符
		Assert.assertTrue(CharSequenceUtil.containsOnly("asdf", 'a', 's', 'd', 'f'));

		// 测试字符串包含testChars中的字符和其它字符
		Assert.assertFalse(CharSequenceUtil.containsOnly("asdf123", 'a', 's', 'd', 'f'));

		// 测试字符串不包含testChars中的任何字符
		Assert.assertFalse(CharSequenceUtil.containsOnly("hello", 'a', 'b'));

		// 测试字符串为null
		Assert.assertTrue(CharSequenceUtil.containsOnly(null, 'a', 'b'));
	}

}

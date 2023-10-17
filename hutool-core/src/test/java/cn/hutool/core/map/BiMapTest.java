package cn.hutool.core.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class BiMapTest {

	@Test
	public void getTest(){
		final BiMap<String, Integer> biMap = new BiMap<>(new HashMap<>());
		biMap.put("aaa", 111);
		biMap.put("bbb", 222);

		Assert.assertEquals(new Integer(111), biMap.get("aaa"));
		Assert.assertEquals(new Integer(222), biMap.get("bbb"));

		Assert.assertEquals("aaa", biMap.getKey(111));
		Assert.assertEquals("bbb", biMap.getKey(222));
	}

	@Test
	public void computeIfAbsentTest(){
		final BiMap<String, Integer> biMap = new BiMap<>(new HashMap<>());
		biMap.put("aaa", 111);
		biMap.put("bbb", 222);

		biMap.computeIfAbsent("ccc", s -> 333);
		Assert.assertEquals(new Integer(333), biMap.get("ccc"));
		Assert.assertEquals("ccc", biMap.getKey(333));
	}

	@Test
	public void putIfAbsentTest(){
		final BiMap<String, Integer> biMap = new BiMap<>(new HashMap<>());
		biMap.put("aaa", 111);
		biMap.put("bbb", 222);

		biMap.putIfAbsent("ccc", 333);
		Assert.assertEquals(new Integer(333), biMap.get("ccc"));
		Assert.assertEquals("ccc", biMap.getKey(333));
	}

	@Test
	public void putExistedValueErrorTest(){
		final BiMap<String, Integer> biMap = new BiMap<>(new HashMap<>());
		try{
		biMap.put("aaa", 111);
		biMap.put("aaa", 222);
		Assert.fail("未抛出异常");
				}
		catch (IllegalArgumentException e){
			System.out.println("捕获");
		}

	}

	@Test
	public void forcePutTest(){
		final BiMap<String, Integer> biMap = new BiMap<>(new HashMap<>());
		biMap.put("aaa", 111);
		biMap.forcePut("aaa", 222);
		Assert.assertEquals(new Integer(222),biMap.get("aaa"));
		Assert.assertEquals(new String("aaa"),biMap.getKey(222));
	}
}

package com.lujunyu.guava.collect;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * 一个具有二级索引的Map。
 *
 * <p>相当于Map<K1,Map<K2,V>>
 *
 * <p>HashBasedTable -> HashMap<K1,HashMap<K2,V>>
 *
 * <p>TreeBasedTable -> TreeMap<K1,TreeMap<k2,V>>
 *
 * <p>ImmutableTable
 *
 * <p>ArrayTable
 */
public class TestTable {

  @Test
  public void testHashMap() {
    HashBasedTable<String, String, String> basedTable = HashBasedTable.create();
    basedTable.put("1", "2", "3");
    System.out.println(basedTable);
    System.out.println(basedTable.get("1", "2"));
  }

  /** 采用二位数组维护的table */
  @Test
  public void testArrayTable() {
    ArrayTable<Integer, Integer, Object> table =
        ArrayTable.create(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2, 3));
    table.set(0, 0, "111");
    System.out.println(table);
  }
}

package com.lujunyu.disrupter;

public class TradeTransaction {
	// 交易id
	private long id;
	// 分为单位。
	private long price;

	public TradeTransaction(long id, long price) {
		super();
		this.id = id;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
}

package com.kata.auction.common;

import java.math.BigDecimal;

/**
 * This is the model that stores winner and its price for given auction
 */
public class Winner {

	Bid bid;
	BigDecimal price;

	public Winner(Bid bid, BigDecimal price) {
		this.bid = bid;
		this.price = price;
	}

	public Bid getBid() {
		return bid;
	}

	public BigDecimal getPrice() {
		return price;
	}

}

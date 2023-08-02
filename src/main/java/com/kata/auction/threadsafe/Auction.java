package com.kata.auction.threadsafe;

import java.math.BigDecimal;

/**
 * This is an auction class it's simply defined by reservePrice.
 * 
 * @author ehmme
 *
 */
public class Auction {
	// Do not sell the object below this price
	private BigDecimal reservePrice;

	public BigDecimal getReservePrice() {
		return reservePrice;
	}

	public Auction(BigDecimal reservePrice) {
		this.reservePrice = reservePrice;
	}

}

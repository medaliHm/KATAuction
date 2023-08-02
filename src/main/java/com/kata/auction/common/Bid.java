package com.kata.auction.common;

import java.math.BigDecimal;

/**
 * model for Bid, bidder (the one who places the bid) & a bid amount
 * 
 * @author ehmme
 *
 */
public class Bid {
	Bidder bidder;
	BigDecimal bidAmount;

	public Bid(Bidder bidder, BigDecimal bidAmount) {
		this.bidder = bidder;
		this.bidAmount = bidAmount;
	}

	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}
}

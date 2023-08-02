package com.kata.auction.second;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.kata.auction.common.Bid;
import com.kata.auction.common.Bidder;

/**
 * Auction Class defined by reservePrice & List of bids on the auction.
 * 
 * @author ehmme
 *
 */
public class Auction {

	private static final Logger LOGGER = Logger.getLogger(Auction.class.getName());

	BigDecimal reservePrice;

	List<Bid> bids = new ArrayList<Bid>();

	public Auction(BigDecimal reservePrice) {
		this.reservePrice = reservePrice;
	}

	/**
	 * Add one or more bids
	 * 
	 * @param bidder    the one who simulate the bid
	 * @param bidPrices one price for each bid
	 */
	public void addNewBids(Bidder bidder, BigDecimal... bidPrices) {

		for (BigDecimal bidPrice : bidPrices) {
			if (bidPrice.compareTo(bids.stream().map(b -> b.getBidAmount()).max(Comparator.naturalOrder())
					.orElse(reservePrice)) >= 0) {
				bids.add(new Bid(bidder, bidPrice));
				LOGGER.info(bidder.getName() + " place a bid of " + bidPrice + " on this auction");
			}
		}
	}

	public BigDecimal getReservePrice() {
		return reservePrice;
	}

	public List<Bid> getBids() {
		return bids;
	}
}

package com.kata.auction.second;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kata.auction.common.AuctionException;
import com.kata.auction.common.Bid;
import com.kata.auction.common.Bidder;
import com.kata.auction.common.Winner;

/**
 * Auction Service to determine winner using java list and stream.
 * 
 * @author ehmme
 *
 */
public class AuctionService_1 {
	
	private static final Logger LOGGER = Logger.getLogger(AuctionService_1.class.getName());

	/**
	 * if no effective bids (bids below reserve or no bids) => exception will be
	 * thrown. Winner will be with the highest bid above reserve. Auction price will
	 * be the second highest price bidded.
	 * 
	 * @param auction
	 * @return Winner of the given auction
	 * @throws AuctionException
	 */

	public Winner determineWinner(Auction auction) throws AuctionException {
		// Filter bids below reserve price
		List<Bid> effectiveBids = auction.getBids().stream()
				.filter((b) -> b.getBidAmount().compareTo(auction.getReservePrice()) >= 0).collect(Collectors.toList());

		// get bidders
		long differentsBidders = effectiveBids.stream().map(Bid::getBidder).distinct().count();
		// no bidders
		if (differentsBidders < 1)
			throw new AuctionException("Auction Terminated because there is no effective bidders");

		// Get the highest bid == winner
		Bid highestBid = effectiveBids.stream().max((b1, b2) -> b1.getBidAmount().compareTo(b2.getBidAmount())).get();
		Bidder highestBuyer = highestBid.getBidder();
		// second highest bid <> winner bid = price of the auction
		BigDecimal secondHighestBid = effectiveBids.stream().filter((b) -> !b.getBidder().equals(highestBuyer))
				.map(b -> b.getBidAmount()).max(Comparator.naturalOrder()).orElse(auction.getReservePrice());
		LOGGER.info(highestBuyer.getName() + " is the winner of this auction with a price of " + secondHighestBid);

		return new Winner(highestBid, secondHighestBid);
	}
}

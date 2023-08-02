package com.kata.auction.threadsafe;

import java.util.logging.Logger;

import com.kata.auction.common.AuctionException;
import com.kata.auction.common.Bid;
import com.kata.auction.common.Winner;

/**
 * 
 * Auction Service that handle all the bid functionality for an auction.
 * 
 * @author ehmme
 *
 */
public class AuctionService {

	private Auction auction;

	private Winner currentWinner;

	private volatile boolean isClosed = false;

	private static final Logger LOGGER = Logger.getLogger(AuctionService.class.getName());

	public AuctionService(Auction auction) {
		this.auction = auction;
	}

	/**
	 * Submits a bid
	 * 
	 * @param bid newBid
	 */
	public synchronized void bid(Bid newBid) throws AuctionException {
		if (isClosed) {
			throw new AuctionException("Auction Already Done");
		}

		LOGGER.info(newBid.getBidder().getName() + " place a bid of " + newBid.getBidAmount() + " on this auction");

		if (currentWinner == null && newBid.getBidAmount().compareTo(auction.getReservePrice()) >= 0) {
			currentWinner = new Winner(newBid, auction.getReservePrice());
		} else if (currentWinner != null
				&& newBid.getBidAmount().compareTo(currentWinner.getBid().getBidAmount()) > 0) {

			currentWinner = new Winner(newBid,
					// if the same bidder price dont change
					(currentWinner.getBid().getBidder().equals(newBid.getBidder()) ? currentWinner.getPrice()
							: currentWinner.getBid().getBidAmount()));
		} else if (currentWinner == null && newBid.getBidAmount().compareTo(auction.getReservePrice()) < 0) {
			LOGGER.warning(newBid.getBidder().toString()
					+ " bid is under the price , it will not be taken part of the auction");
		}

	}

	/**
	 * Closes this auction and get the result.
	 * 
	 * @return winner or exception if reserve not met
	 */
	public Winner close() {

		if (!isClosed) {
			synchronized (AuctionService.class) {
				if (!isClosed) {
					isClosed = true;
				}
			}
		}

		if (currentWinner != null) {
			LOGGER.info(currentWinner.getBid().getBidder() + " is the winner of this auction with a price of "
					+ currentWinner.getPrice());
		} else {
			LOGGER.warning("No effective Bid was received!!");

		}

		return currentWinner;
	}
}

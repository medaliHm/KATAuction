package com.kata.auction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kata.auction.common.AuctionException;
import com.kata.auction.common.Bid;
import com.kata.auction.common.Bidder;
import com.kata.auction.common.Winner;
import com.kata.auction.threadsafe.Auction;
import com.kata.auction.threadsafe.AuctionService;

class AuctionServiceTest {

	private AuctionService auctionService;

	
	private Auction auction;
	
	private static final Logger LOGGER = Logger.getLogger(AuctionServiceTest.class.getName());

	@BeforeEach
	public void setup() {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

		BigDecimal reservePrice = new BigDecimal("100.00");
		auction = new Auction(reservePrice);
		auctionService = new AuctionService(auction);
	}

	@Test
	public void noBids() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");
		assertNull(auctionService.close());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void testIfReserveisNotBidded() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		Bidder bidder = new Bidder("Mohamed");
		Bid newBid = new Bid(bidder, new BigDecimal("90.00"));
		auctionService.bid(newBid);

		assertNull(auctionService.close());
	}

	@Test
	public void testOneBidReserveMet() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		Bidder bidder = new Bidder("Hugo");
		Bid newBid = new Bid(bidder, new BigDecimal("200.00"));
		auctionService.bid(newBid);
		Winner winner = auctionService.close();
		assertEquals("Hugo", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void testOnlyOneBidEqualsReservePrice() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		Bidder bidderHugo = new Bidder("Hugo");
		Bid hugoBid = new Bid(bidderHugo, new BigDecimal("100.00"));
		auctionService.bid(hugoBid);
		Winner winner = auctionService.close();
		assertEquals("Hugo", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void testTwoBidsFirstOneBelowReserve() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		Bidder bidderHugo = new Bidder("Hugo");
		Bid hugoBid = new Bid(bidderHugo, new BigDecimal("80.00"));
		auctionService.bid(hugoBid);

		Bidder bidderAlain = new Bidder("Alain");
		Bid alainBid = new Bid(bidderAlain, new BigDecimal("200.00"));
		auctionService.bid(alainBid);

		Winner winner = auctionService.close();
		assertEquals("Alain", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void testTwoBidsAboveReserveDecremental() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		Bidder bidderAlain = new Bidder("Alain");
		Bid alainBid = new Bid(bidderAlain, new BigDecimal("200.00"));
		auctionService.bid(alainBid);

		Bidder bidderHugo = new Bidder("Hugo");
		Bid hugoBid = new Bid(bidderHugo, new BigDecimal("120.00"));

		auctionService.bid(hugoBid);

		Winner winner = auctionService.close();
		assertEquals("Alain", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void testTwoBidsAboveReserveIncremental() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		Bidder bidderHugo = new Bidder("Hugo");
		Bid hugoBid = new Bid(bidderHugo, new BigDecimal("200.00"));

		Bidder bidderAlain = new Bidder("Alain");
		Bid alainBid = new Bid(bidderAlain, new BigDecimal("300.00"));

		auctionService.bid(hugoBid);
		auctionService.bid(alainBid);

		Winner winner = auctionService.close();
		assertEquals("Alain", winner.getBid().getBidder().getName());
		assertEquals(new BigDecimal("200.00"), winner.getPrice());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void testExampleOnKATA() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		// Bidders Initiliazation
		Bidder bidderA = new Bidder("A");
		Bidder bidderC = new Bidder("C");
		Bidder bidderD = new Bidder("D");
		Bidder bidderE = new Bidder("E");

		// Bids Init
		Bid aBid_1 = new Bid(bidderA, new BigDecimal("110.00"));
		Bid aBid_2 = new Bid(bidderA, new BigDecimal("130.00"));

		Bid cBid = new Bid(bidderC, new BigDecimal("125.00"));

		Bid dBid_1 = new Bid(bidderD, new BigDecimal("105.00"));
		Bid dBid_2 = new Bid(bidderD, new BigDecimal("115.00"));
		Bid dBid_3 = new Bid(bidderD, new BigDecimal("90.00"));

		Bid eBid_1 = new Bid(bidderE, new BigDecimal("132.00"));
		Bid eBid_2 = new Bid(bidderE, new BigDecimal("135.00"));
		Bid eBid_3 = new Bid(bidderE, new BigDecimal("140.00"));

		auctionService.bid(aBid_1);
		auctionService.bid(aBid_2);
		auctionService.bid(cBid);
		auctionService.bid(dBid_1);
		auctionService.bid(dBid_2);
		auctionService.bid(dBid_3);
		auctionService.bid(eBid_1);
		auctionService.bid(eBid_2);
		auctionService.bid(eBid_3);

		Winner winner = auctionService.close();
		assertEquals("E", winner.getBid().getBidder().getName());
		assertEquals(new BigDecimal("130.00"), winner.getPrice());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}

	@Test
	public void biddingOnAClosedAuction() {
		LOGGER.info(
				"****************************************************************TEST CASE************************************************************");

		AuctionException thrown = assertThrows(AuctionException.class, () -> {
			auctionService.close();
			Bidder bidderHugo = new Bidder("Hugo");
			Bid hugoBid = new Bid(bidderHugo, new BigDecimal("120.00"));
			auctionService.bid(hugoBid);

		});

		assertEquals("Auction Already Done", thrown.getMessage());
		LOGGER.info(
				"****************************************************************END TEST CASE************************************************************");

	}
}

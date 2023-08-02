package com.kata.auction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kata.auction.common.AuctionException;
import com.kata.auction.common.Bidder;
import com.kata.auction.common.Winner;
import com.kata.auction.second.Auction;
import com.kata.auction.second.AuctionService_1;

class AuctionServiceSecondTest {

	private AuctionService_1 auctionService;

	private Auction auction;

	@BeforeEach
	public void setup() {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

		BigDecimal reservePrice = new BigDecimal("100.00");
		auction = new Auction(reservePrice);
		auctionService = new AuctionService_1();
	}

	@Test
	public void noBids() {
		AuctionException thrown = assertThrows(AuctionException.class, () -> {
			auctionService.determineWinner(auction);
		});

		assertEquals("Auction Terminated because there is no effective bidders", thrown.getMessage());
	}

	@Test
	public void testIfReserveisNotBidded() {

		Bidder bidder = new Bidder("Mohamed");
		AuctionException thrown = assertThrows(AuctionException.class, () -> {
			auction.addNewBids(bidder, new BigDecimal("90.00"));
			Winner winner = auctionService.determineWinner(auction);
		});

		assertEquals("Auction Terminated because there is no effective bidders", thrown.getMessage());

	}

	@Test
	public void testOneBidReserveMet() {
		Bidder bidder = new Bidder("Hugo");
		auction.addNewBids(bidder, new BigDecimal("200.00"));
		Winner winner = auctionService.determineWinner(auction);
		assertEquals("Hugo", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());
	}

	@Test
	public void testOnlyOneBidEqualsReservePrice() {
		Bidder bidderHugo = new Bidder("Hugo");
		auction.addNewBids(bidderHugo, new BigDecimal("100.00"));

		Winner winner = auctionService.determineWinner(auction);
		assertEquals("Hugo", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());

	}

	@Test
	public void testTwoBidsFirstOneBelowReserve() {
		Bidder bidderHugo = new Bidder("Hugo");
		auction.addNewBids(bidderHugo, new BigDecimal("80.00"));

		Bidder bidderAlain = new Bidder("Alain");
		auction.addNewBids(bidderAlain, new BigDecimal("200.00"));

		Winner winner = auctionService.determineWinner(auction);
		assertEquals("Alain", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());

	}

	@Test
	public void testTwoBidsAboveReserveIncremental() {

		Bidder bidderAlain = new Bidder("Alain");
		auction.addNewBids(bidderAlain, new BigDecimal("200.00"));

		Bidder bidderHugo = new Bidder("Hugo");

		auction.addNewBids(bidderHugo, new BigDecimal("120.00"));

		Winner winner = auctionService.determineWinner(auction);
		assertEquals("Alain", winner.getBid().getBidder().getName());
		assertEquals(auction.getReservePrice(), winner.getPrice());

	}

	@Test
	public void testTwoBidsAboveReserveDecremental() {
		Bidder bidderHugo = new Bidder("Hugo");
		auction.addNewBids(bidderHugo, new BigDecimal("200.00"));

		Bidder bidderAlain = new Bidder("Alain");
		auction.addNewBids(bidderAlain, new BigDecimal("300.00"));

		Winner winner = auctionService.determineWinner(auction);
		assertEquals("Alain", winner.getBid().getBidder().getName());
		assertEquals(new BigDecimal("200.00"), winner.getPrice());

	}

	@Test
	public void testExampleOnKATA() {
		// Bidders Initiliazation
		Bidder bidderA = new Bidder("A");
		Bidder bidderC = new Bidder("C");
		Bidder bidderD = new Bidder("D");
		Bidder bidderE = new Bidder("E");

		auction.addNewBids(bidderA, new BigDecimal("110"), new BigDecimal("130"));
		auction.addNewBids(bidderC, new BigDecimal("125"));
		auction.addNewBids(bidderD, new BigDecimal("105"), new BigDecimal("115"), new BigDecimal("90"));
		auction.addNewBids(bidderE, new BigDecimal("132"), new BigDecimal("135"), new BigDecimal("140"));

		Winner winner = auctionService.determineWinner(auction);
		assertEquals("E", winner.getBid().getBidder().getName());
		assertEquals(new BigDecimal("130"), winner.getPrice());
	}

}

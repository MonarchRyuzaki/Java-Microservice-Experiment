package com.shiryu21.marketdataservice.service;

import com.shiryu21.grpc.MarketDataServiceGrpc;
import com.shiryu21.grpc.PriceRequest;
import com.shiryu21.grpc.PriceResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CryptoPriceService extends MarketDataServiceGrpc.MarketDataServiceImplBase {
    @Override
    public void getCurrentPrice(PriceRequest request, StreamObserver<PriceResponse> responseObserver) {
        String coin = request.getSymbol();
        System.out.println("Received request for : " + coin);

        double price = 0.0;
        if (coin.equalsIgnoreCase("BTC")) {
            price = 50000.00;
        } else if (coin.equalsIgnoreCase("ETH")) {
            price = 3000.00;
        }

        PriceResponse response = PriceResponse.newBuilder().setSymbol(coin).setPrice(price).setTimestamp(java.time.Instant.now().toString()).build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }
}

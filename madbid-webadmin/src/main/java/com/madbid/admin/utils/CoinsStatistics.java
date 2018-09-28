package com.madbid.admin.utils;

import com.madbid.core.model.Coin;
import com.madbid.core.model.User;
import org.primefaces.model.chart.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dimer on 8/16/14.
 */
public class CoinsStatistics {

    private User user;
    private Set<BigDecimal> coinValues;
    private HashMap<BigDecimal, ArrayList<Coin>> coinMap;
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel = new HorizontalBarChartModel();

    public CoinsStatistics(User user) {
        this.user = user;
        horizontalBarModel = new HorizontalBarChartModel();

        // Find unique coin values
        coinValues = new HashSet();
        coinMap = new HashMap<>();
        for (Coin coin : user.getCoins()) {
            coinValues.add(coin.getValue());
            if (coinMap.containsKey(coin.getValue())) {
                coinMap.get(coin.getValue()).add(coin);
            } else {
                ArrayList<Coin> coinList = new ArrayList();
                coinList.add(coin);
                coinMap.put(coin.getValue(), coinList);
            }
        }

        // Popualte charSeries data
        ChartSeries coinValueSeries = new ChartSeries();
        coinValueSeries.setLabel("Value");
        for (BigDecimal coinValue : coinValues) {
            coinValueSeries.set(coinValue.toString(), coinMap.get(coinValue).size());
        }
        horizontalBarModel.addSeries(coinValueSeries);
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Values");
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Quantities");
    }

    public CoinsStatistics() {

    }

    public User getUser() {
        return user;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
        this.horizontalBarModel = horizontalBarModel;
    }

}

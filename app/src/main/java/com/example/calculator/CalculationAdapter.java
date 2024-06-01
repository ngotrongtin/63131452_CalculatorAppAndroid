package com.example.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationAdapter.CalculationViewHolder> {
    private List<Calculation> calculationList;

    public CalculationAdapter(List<Calculation> calculationList) {
        this.calculationList = calculationList;
    }

    @Override
    public CalculationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculation, parent, false);
        return new CalculationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalculationViewHolder holder, int position) {
        Calculation calculation = calculationList.get(position);
        holder.textExpression.setText(calculation.getExpression());
        holder.textResult.setText(String.valueOf(calculation.getResult()));
    }

    @Override
    public int getItemCount() {
        return calculationList.size();
    }

    public static class CalculationViewHolder extends RecyclerView.ViewHolder {
        public TextView textExpression;
        public TextView textResult;

        public CalculationViewHolder(View itemView) {
            super(itemView);
            textExpression = itemView.findViewById(R.id.textExpression);
            textResult = itemView.findViewById(R.id.textResult);
        }
    }
}
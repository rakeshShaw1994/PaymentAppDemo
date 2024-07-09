package com.example.test.interfaces;

import android.view.View;

public interface NumberPadListener {
    void onNumberInput(int input);
    void onEraseClicked();
    void onNextClicked();
}

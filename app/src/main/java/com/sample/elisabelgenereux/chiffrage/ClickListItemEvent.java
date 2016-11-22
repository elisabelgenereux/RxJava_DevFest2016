package com.sample.elisabelgenereux.chiffrage;


public class ClickListItemEvent {
    boolean becomeChosen;
    JsLibrary library;

    ClickListItemEvent(boolean viewIsSelected, JsLibrary clickedLib) {
        becomeChosen = viewIsSelected;
        library = clickedLib;
    }
}

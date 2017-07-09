package com.porto.isabel.flickrimageapp.searchimages.presentation.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SearchImagesSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.porto.isabel.flickrimageapp.searchimages.presentation.provider.SearchImagesSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchImagesSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
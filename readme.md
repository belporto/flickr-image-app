# flickr-image-app

This is an app that uses the Flickr image search API and shows the results in a 3­column scrollable view.

* The app supports endless scrolling, automatically requesting and displaying more images when the user scrolls to the bottom of the view.
* The app allows you to see a history of past searches, by using SearchRecentSuggestionsProvider.
* The app handles orientation changes.
* The app was written using Viper architecture.
* Third­party Libraries: Picasso, Dagger, RxJava, Retrofit.

To run this project it's necessary to add a new file `keys.xml` into the `values` folder.
This file should contain the key "flickr_api_key"

```
<resources>
     <string name="flickr_api_key"><your api key></string>
</resources>
```

You can get your API key here: https://www.flickr.com/services/api/misc.api_keys.html.


# Barcode Scanner

This is a demo app that allows a user to either scan a barcode (using the device camera), as well as to manually enter a barcode. This then saves the results to a SQLite db using the recently released at Google I/O '17 Android Architecture Component's Room Persistence Library. Results are displayed in a list using RecyclerView.

Libraries and tools included:

* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Glide](https://github.com/bumptech/glide)
* [MVBarcodeReader](https://github.com/Credntia/MVBarcodeReader)
* [Butterknife](https://github.com/JakeWharton/butterknife)
* [Gson](https://github.com/google/gson)
* [Dagger 2](https://github.com/google/dagger)
* [Timber](https://github.com/JakeWharton/timber)
* [Retrofit 2](http://square.github.io/retrofit/)
* [Material Dialogs](https://github.com/afollestad/material-dialogs)
* [Play Services](https://developers.google.com/android/guides/setup)
* [Support Library](https://developer.android.com/topic/libraries/support-library/packages.html)
* [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
* [AutoValue](https://github.com/google/auto/tree/master/value) with extensions [AutoValueParcel](https://github.com/rharter/auto-value-parcel) and [AutoValueGson](https://github.com/rharter/auto-value-gson)
* [Mockito](http://site.mockito.org/)

# Acknowledgments
* [Barcode Api](http://www.upcitemdb.com/) - Used as the api to get questions.

# Demo Video
Watch a demo video below (old)

[![Demo](https://img.youtube.com/vi/RujHKpBKIB4/0.jpg)](https://www.youtube.com/watch?v=RujHKpBKIB4)

# License
MIT License

Copyright (c) 2017 Matthew Regis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

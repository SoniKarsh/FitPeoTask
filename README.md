<p align="center">
  <a href="" rel="noopener">
  <a href="https://imgbb.com/"><img width=200px height=200px src="https://i.ibb.co/BrKFFW3/1.png" alt="1" border="0"></a>
  <a href="https://imgbb.com/"><img width=200px height=200px src="https://i.ibb.co/WWPXxw3/2.png" alt="2" border="0"></a>
</p>

<h1 align="center">FitPeo</h1>
<p align="center"> Write a android machine test with calling get API and populated all the data in recycler view, on tap detail activity open with header image, title and description.
    <br> 
</p>

## 📝 Table of Contents
- [About](#about)
- [Components Used](#components)
- [Implementations](#implementations)

## About <a name = "about"></a>
The project is simply about calling an API to retrieve data which is consists of list of objects containing information of albums. Each album contains 50 items.
Each item has its own unique id and thumbnail url, url and title. There are somewhere around 3000 images and to retrieve and show these lists optimally we
need to use pagination. Using pagination we can load one album at a time and make user's UI/UX better. Once user reaches end of the list we can load another album.
To make it more efficient we have loaded thumbnail on the list and once user clicks on any item we will redirect them to detail screen which will show
image, title and index and it'll load bigger image. To enhance UX i have used some animation to show user a transition between list and a detail screen.
Implemented a splash screen where we have fetched the first item in the splash screen it self so, that user wouln't have to wait on home screen once they reach there.

## Components Used <a name = "components"></a>
- MVVM Architecture
- Paging 3
- View binding - List screen
- Data binding - Detail screen
- Flow, Coroutines
- Unit testing to test pagination (Mockito)
- Kotlin Koin for DI (Dependency Injection)
- Picasso for image loading
- Retrofit - Api calling
- Splash screen API - To give generic UX for all the versions
- Navigation graph

## Implementations <a name = "implementations"></a>
Used two different approached one with fragments and other with the activities.
main -> It contains fragment approach where i have used one activity to host two fragments
HomeList and Details fragment.

feature/with_activity -> It contains activity based approach where i have used two different activities one for the list and one for the details.
MainActivity & DetailsActivity.


# EBookApplication

EBookApplication is an Android application that allows users to register, login, read books, add bookmarks and notes, browse recommended books, download books, rate books, and save or love bookmarks. Admin users have additional capabilities to manage books and categories.

## Features

### User Features

- **Registration and Login**: Users can register and login using Firebase Authentication.
- **Read Books**: Users can read books stored in the application.
- **Add Bookmarks**: Users can add bookmarks to books they are reading.
- **Add Notes**: Users can add notes to their bookmarks for future reference.
- **Browse Recommended Books**: Users can browse books recommended based on their reading history.
- **Download Books**: Users can download books for offline reading.
- **Rate Books**: Users can rate books they have read.
- **Browse Recommended Bookmarks**: Users can browse bookmarks recommended based on previous reading attempts.
- **Save or Love Bookmarks**: Users can save or mark bookmarks as loved.

### Admin Features

- **Manage Books**: Admins can manage the books available in the application, including adding, updating, or removing book pages.
- **Manage Categories**: Admins can manage the categories under which books are listed.

## Technologies Used

- **Firebase Authentication**: Used for user registration and login.
- **Firebase Firestore**: Used to store user data, books, bookmarks, notes, and ratings.
- **MVVM Pattern**: Implemented to separate the user interface from the business logic and data handling.
- **Room Database**: Used for storing downloaded books for offline access.

## Installation and Setup

### Clone the Repository

```sh
git clone https://github.com/yourusername/EBookApplication.git
cd EBookApplication

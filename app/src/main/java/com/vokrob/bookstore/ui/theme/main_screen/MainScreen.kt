package com.vokrob.bookstore.ui.theme.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vokrob.bookstore.data.Book
import com.vokrob.bookstore.data.Favorite
import com.vokrob.bookstore.ui.theme.login.data.MainScreenDataObject
import com.vokrob.bookstore.ui.theme.main_screen.bottom_menu.BottomMenu
import com.vokrob.bookstore.ui.theme.main_screen.bottom_menu.BottomMenuItem
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    onBookEditClick: (Book) -> Unit,
    onBookClick: (Book) -> Unit,
    onAdminClick: () -> Unit
) {
    val db = remember { Firebase.firestore }
    val coroutineScope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val booksListState = remember { mutableStateOf(emptyList<Book>()) }
    val isAdminState = remember { mutableStateOf(false) }
    val selectedBottomItemState = remember { mutableStateOf(BottomMenuItem.Home.title) }
    val isFavListEmptyState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getAllFavsIds(db, navData.uid) { favs ->
            getAllBooks(db, favs) { books ->
                isFavListEmptyState.value = books.isEmpty()
                booksListState.value = books
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(navData.email)

                DrawerBody(
                    onAdmin = { isAdmin -> isAdminState.value = isAdmin },
                    onFavClick = {
                        selectedBottomItemState.value = BottomMenuItem.Favs.title

                        getAllFavsIds(db, navData.uid) { favs ->
                            getAllFavsBooks(db, favs) { books ->
                                isFavListEmptyState.value = books.isEmpty()
                                booksListState.value = books
                            }
                        }
                        coroutineScope.launch { drawerState.close() }
                    },
                    onAdminClick = {
                        coroutineScope.launch { drawerState.close() }
                        onAdminClick()
                    },
                    onCategoryClick = { category ->
                        getAllFavsIds(db, navData.uid) { favs ->
                            if (category == "All") {
                                getAllBooks(db, favs) { books ->
                                    booksListState.value = books
                                }
                            } else {
                                getAllBooksFromCategory(db, favs, category) { books ->
                                    booksListState.value = books
                                }
                            }
                        }
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu(
                    selectedBottomItemState.value,
                    onFavsClick = {
                        selectedBottomItemState.value = BottomMenuItem.Favs.title

                        getAllFavsIds(db, navData.uid) { favs ->
                            getAllFavsBooks(db, favs) { books ->
                                isFavListEmptyState.value = books.isEmpty()
                                booksListState.value = books
                            }
                        }
                    },
                    onHomeClick = {
                        selectedBottomItemState.value = BottomMenuItem.Home.title

                        getAllFavsIds(db, navData.uid) { favs ->
                            getAllBooks(db, favs) { books ->
                                isFavListEmptyState.value = books.isEmpty()
                                booksListState.value = books
                            }
                        }
                    }
                )
            }
        ) { paddingValue ->
            if (isFavListEmptyState.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Empty list",
                        color = Color.LightGray
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                items(booksListState.value) { book ->
                    BookListItemUi(
                        isAdminState.value,
                        book,
                        onBookClick = { bk -> onBookClick(bk) },
                        onEditClick = { onBookEditClick(it) },
                        onFavClick = {
                            booksListState.value = booksListState.value.map { bk ->
                                if (bk.key == book.key) {
                                    onFavs(
                                        db,
                                        navData.uid,
                                        Favorite(bk.key),
                                        !bk.isFavorite
                                    )
                                    bk.copy(isFavorite = !bk.isFavorite)
                                } else bk
                            }
                            if (selectedBottomItemState.value == BottomMenuItem.Favs.title) {
                                booksListState.value = booksListState.value.filter { it.isFavorite }
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun getAllBooksFromCategory(
    db: FirebaseFirestore,
    idsList: List<String>,
    category: String,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .whereEqualTo("category", category)
        .get()
        .addOnSuccessListener { task ->
            val bookList = task.toObjects(Book::class.java).map {
                if (idsList.contains(it.key)) it.copy(isFavorite = true)
                else it
            }
            onBooks(bookList)
        }
}

private fun getAllBooks(
    db: FirebaseFirestore,
    idsList: List<String>,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .get()
        .addOnSuccessListener { task ->
            val bookList = task.toObjects(Book::class.java).map {
                if (idsList.contains(it.key)) it.copy(isFavorite = true)
                else it
            }
            onBooks(bookList)
        }
}

private fun getAllFavsBooks(
    db: FirebaseFirestore,
    idsList: List<String>,
    onBooks: (List<Book>) -> Unit
) {
    if (idsList.isNotEmpty()) {
        db.collection("books")
            .whereIn(FieldPath.documentId(), idsList)
            .get()
            .addOnSuccessListener { task ->
                val bookList = task.toObjects(Book::class.java).map {
                    if (idsList.contains(it.key)) it.copy(isFavorite = true)
                    else it
                }
                onBooks(bookList)
            }
    } else onBooks(emptyList())
}

private fun getAllFavsIds(
    db: FirebaseFirestore,
    uid: String,
    onFavs: (List<String>) -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("favs")
        .get()
        .addOnSuccessListener { task ->
            val idsList = task.toObjects(Favorite::class.java)
            val keyList = arrayListOf<String>()

            idsList.forEach { keyList.add(it.key) }
            onFavs(keyList)
        }
}

private fun onFavs(
    db: FirebaseFirestore,
    uid: String,
    favorite: Favorite,
    isFav: Boolean,
) {
    if (isFav) {
        db.collection("users")
            .document(uid)
            .collection("favs")
            .document(favorite.key)
            .set(favorite)
    } else {
        db.collection("users")
            .document(uid)
            .collection("favs")
            .document(favorite.key)
            .delete()
    }
}
























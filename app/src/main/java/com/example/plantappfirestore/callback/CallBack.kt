package com.example.plantappfirestore.callback

class CallBack {
    interface Alphabet {
        fun alphabet(alphabet: String)
    }

    interface SpeciesDetail {
        fun speciesDetail(
            category: String,
            title: String,
            description: String,
            kingdom: String,
            family: String,
            star: String,
            image: String,
            heart: String)
    }

    interface ArticlesDetail {
        fun articlesDetail(
            title: String,
            description: String,
            name: String,
            avatar: String,
            date: String,
            image: String,
            heart: String)
    }
}
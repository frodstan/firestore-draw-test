package com.example.nrk_firebase_test

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import java.util.*

class FirebaseRepository {
    private val heatMapCollection get() = FirebaseFirestore.getInstance().collection("heatmap")

    val updateFlow = flow<Timestamp> {
        while (true) {
            val timestamp = Timestamp(Date(System.currentTimeMillis() - 10_000))
            emit(timestamp)
            delay(1_000)
        }
    }

    fun getClickPoints() = heatMapCollection
            .snapshots()
            .mapLatest { snapshot -> snapshot.toObjects<ClickPoint>() }
            .combine(updateFlow) { snapshots, timestamp ->
                snapshots.filter { it.timestamp > timestamp }
            }

    fun addClickPoint(clickPoint: ClickPoint) {
        heatMapCollection.add(clickPoint)
    }

    fun clear() {

    }
}

data class ClickPoint(
    val x: Int = 0,
    val y: Int = 0,
    val timestamp: Timestamp = Timestamp.now()
)
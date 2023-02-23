package com.example.aop_part6_chapter01.data.repository.map

import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.response.address.AddressInfo

interface MapRepository {

    suspend fun getReverseGeoInformation(locationLatLngEntity: LocationLatLngEntity): AddressInfo?

}
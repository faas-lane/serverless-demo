package com.bytekast.serverless.service

import groovy.transform.Memoized
import groovy.util.logging.Log4j
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient


@Singleton
@Log4j
class RestaurantService {

  private final rest = new RESTClient('https://developers.zomato.com/')

  @Memoized
  List search(Integer cityId, String cuisines = null, Integer radius = 5) {

    def allCuisines = getCuisinesByCity(cityId)
    def cuisineSelections = cuisines?.split(',')?.collect { it.toUpperCase().trim() }
    def filteredCuisines = allCuisines?.findAll { it.name?.toUpperCase().trim() in cuisineSelections }.id?.join(',')

    def headers = ['user_key': System.getenv('ZOMATO_API_KEY')]
    def query = [
        'entity_id'  : cityId,
        'entity_type': 'city',
        'radius'     : radius,
        'cuisines'   : filteredCuisines,
        'sort'       : 'real_distance'
    ]
    def resp = rest.get(
        headers: headers,
        path: "api/v2.1/search",
        requestContentType: ContentType.JSON,
        query: query)

    resp.data?.restaurants?.collect {
      "${it.restaurant.name}(${it.restaurant.currency}) | ${it.restaurant.location.address}"
    }
  }

  @Memoized
  List getCuisinesByCity(Integer cityId) {
    def headers = ['user_key': System.getenv('ZOMATO_API_KEY')]
    def query = [
        'city_id': cityId
    ]
    def resp = rest.get(
        headers: headers,
        path: "api/v2.1/cuisines",
        requestContentType: ContentType.JSON,
        query: query)
    resp.data?.cuisines?.collect {
      [
          id  : it.cuisine.cuisine_id,
          name: it.cuisine.cuisine_name
      ]
    }
  }
}

package com.aragh.kotlin2.data


class NotFoundException(entity: String, id: String)
  : RuntimeException("$entity with id $id was not found")
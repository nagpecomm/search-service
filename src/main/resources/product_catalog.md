
DELETE /products


PUT products
{
  "settings": {
    "analysis": {
      "analyzer": {
        "autocomplete_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": ["lowercase", "edge_ngram_filter"]
        }
      },
      "filter": {
        "edge_ngram_filter": {
          "type": "edge_ngram",
          "min_gram": 2,
          "max_gram": 20
        }
      }
    }
  },
  "mappings": {
    "properties": {

      "productId": { "type": "long" },
      "name": {
        "type": "text",
        "analyzer": "autocomplete_analyzer"
      },
      "sku": { "type": "keyword" },
      "price": { "type": "double" },
      "available": { "type": "boolean" },

      "manufacturer": {
        "properties": {
          "manufacturerId": { "type": "long" },
          "name": { "type": "keyword" }
        }
      },

      "categories": {
        "type": "nested",
        "properties": {
          "categoryId": { "type": "long" },
          "name": { "type": "keyword" }
        }
      },

      "rating": { "type": "double" },
      "reviewCount": { "type": "integer" },

      "suggest": {
        "type": "completion"
      }
    }
  }
}

POST products/_doc/1001
{
  "productId": 1001,
  "name": "iPhone 15 Pro",
  "sku": "IPHONE15PRO",
  "price": 1299,
  "available": true,
  "rating": 4.8,
  "reviewCount": 350,

  "manufacturer": {
    "manufacturerId": 1,
    "name": "Apple"
  },

  "categories": [
    { "categoryId": 10, "name": "Mobiles" },
    { "categoryId": 1, "name": "Electronics" }
  ],

  "suggest": {
    "input": ["iphone 15 pro", "iphone", "apple phone"]
  }
}


POST products/_doc/1002
{
  "productId": 1002,
  "name": "Samsung Galaxy S24",
  "sku": "GALAXYS24",
  "price": 999,
  "available": true,
  "rating": 4.6,
  "reviewCount": 280,

  "manufacturer": {
    "manufacturerId": 2,
    "name": "Samsung"
  },

  "categories": [
    { "categoryId": 10, "name": "Mobiles" },
    { "categoryId": 1, "name": "Electronics" }
  ],

  "suggest": {
    "input": ["galaxy s24", "samsung phone"]
  }
}

POST products/_doc/1003
{
  "productId": 1003,
  "name": "Sony Wireless Headphones",
  "sku": "SONYWH1000",
  "price": 299,
  "available": true,
  "rating": 4.7,
  "reviewCount": 150,

  "manufacturer": {
    "manufacturerId": 3,
    "name": "Sony"
  },

  "categories": [
    { "categoryId": 20, "name": "Headphones" },
    { "categoryId": 1, "name": "Electronics" }
  ],

  "suggest": {
    "input": ["sony headphones", "wireless headphones"]
  }
}


GET products/_search
{
  "query": {
    "multi_match": {
      "query": "iphone",
      "fields": [
        "name",
        "manufacturer.name",
        "categories.name"
      ]
    }
  }
}

GET products/_search
{
  "suggest": {
    "product-suggest": {
      "prefix": "iph",
      "completion": {
        "field": "suggest"
      }
    }
  }
}

GET products/_search
{
  "suggest": {
    "product-suggest": {
      "prefix": "iph",
      "completion": {
        "field": "suggest"
      }
    }
  }
}

GET products/_search
{
  "query": {
    "range": {
      "price": {
        "gte": 500,
        "lte": 1500
      }
    }
  }
}

GET products/_search
{
  "query": {
    "term": {
      "manufacturer.name": "Apple"
    }
  }
}

GET products/_search
{
  "size": 0,
  "aggs": {
    "brands": {
      "terms": {
        "field": "manufacturer.name"
      }
    }
  }
}

GET products/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "multi_match": {
            "query": "iphone",
            "fields": ["name"]
          }
        }
      ],
      "filter": [
        {
          "range": {
            "price": {
              "lte": 1500
            }
          }
        }
      ]
    }
  }
}



!!!!!!!!!

POST _bulk
{ "index": { "_index": "products", "_id": "2001" } }
{ "productId":2001,"name":"iPhone 15","sku":"IPHONE15","price":999,"available":true,"rating":4.7,"reviewCount":420,"manufacturer":{"manufacturerId":1,"name":"Apple"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["iphone","iphone 15","apple phone"]}}
{ "index": { "_index": "products", "_id": "2002" } }
{ "productId":2002,"name":"iPhone 15 Pro Max","sku":"IPHONE15PM","price":1399,"available":true,"rating":4.9,"reviewCount":500,"manufacturer":{"manufacturerId":1,"name":"Apple"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["iphone pro max","apple phone"]}}
{ "index": { "_index": "products", "_id": "2003" } }
{ "productId":2003,"name":"Samsung Galaxy S23","sku":"GALAXYS23","price":899,"available":true,"rating":4.6,"reviewCount":330,"manufacturer":{"manufacturerId":2,"name":"Samsung"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["galaxy","samsung galaxy"]}}
{ "index": { "_index": "products", "_id": "2004" } }
{ "productId":2004,"name":"Samsung Galaxy S24 Ultra","sku":"S24ULTRA","price":1299,"available":true,"rating":4.8,"reviewCount":350,"manufacturer":{"manufacturerId":2,"name":"Samsung"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["galaxy ultra","samsung phone"]}}
{ "index": { "_index": "products", "_id": "2005" } }
{ "productId":2005,"name":"Google Pixel 8","sku":"PIXEL8","price":799,"available":true,"rating":4.6,"reviewCount":200,"manufacturer":{"manufacturerId":8,"name":"Google"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["pixel phone","google phone"]}}
{ "index": { "_index": "products", "_id": "2006" } }
{ "productId":2006,"name":"OnePlus 12 Pro","sku":"ONEPLUS12PRO","price":949,"available":true,"rating":4.6,"reviewCount":210,"manufacturer":{"manufacturerId":9,"name":"OnePlus"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["oneplus","oneplus phone"]}}
{ "index": { "_index": "products", "_id": "2007" } }
{ "productId":2007,"name":"Xiaomi 14","sku":"XIAOMI14","price":699,"available":true,"rating":4.5,"reviewCount":190,"manufacturer":{"manufacturerId":10,"name":"Xiaomi"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["xiaomi phone"]}}
{ "index": { "_index": "products", "_id": "2008" } }
{ "productId":2008,"name":"Realme GT 5 Pro","sku":"REALMEGT5PRO","price":599,"available":true,"rating":4.4,"reviewCount":150,"manufacturer":{"manufacturerId":11,"name":"Realme"},"categories":[{"categoryId":10,"name":"Mobiles"}],"suggest":{"input":["realme phone"]}}

POST _bulk
{ "index": { "_index": "products", "_id": "2010" } }
{ "productId":2010,"name":"MacBook Air M3","sku":"MACBOOKAIRM3","price":1299,"available":true,"rating":4.9,"reviewCount":410,"manufacturer":{"manufacturerId":1,"name":"Apple"},"categories":[{"categoryId":30,"name":"Laptops"}],"suggest":{"input":["macbook air","apple laptop"]}}
{ "index": { "_index": "products", "_id": "2011" } }
{ "productId":2011,"name":"MacBook Pro 16","sku":"MACBOOKPRO16","price":2599,"available":true,"rating":4.9,"reviewCount":300,"manufacturer":{"manufacturerId":1,"name":"Apple"},"categories":[{"categoryId":30,"name":"Laptops"}],"suggest":{"input":["macbook pro"]}}
{ "index": { "_index": "products", "_id": "2012" } }
{ "productId":2012,"name":"Dell XPS 13","sku":"DELLXPS13","price":1599,"available":true,"rating":4.7,"reviewCount":240,"manufacturer":{"manufacturerId":4,"name":"Dell"},"categories":[{"categoryId":30,"name":"Laptops"}],"suggest":{"input":["dell laptop","xps laptop"]}}
{ "index": { "_index": "products", "_id": "2013" } }
{ "productId":2013,"name":"Dell XPS 17","sku":"DELLXPS17","price":2199,"available":true,"rating":4.7,"reviewCount":180,"manufacturer":{"manufacturerId":4,"name":"Dell"},"categories":[{"categoryId":30,"name":"Laptops"}],"suggest":{"input":["dell xps"]}}
{ "index": { "_index": "products", "_id": "2014" } }
{ "productId":2014,"name":"HP Spectre x360","sku":"HPSPECTRE","price":1499,"available":true,"rating":4.6,"reviewCount":210,"manufacturer":{"manufacturerId":5,"name":"HP"},"categories":[{"categoryId":30,"name":"Laptops"}],"suggest":{"input":["hp laptop"]}}
{ "index": { "_index": "products", "_id": "2015" } }
{ "productId":2015,"name":"Lenovo ThinkPad X1","sku":"THINKPADX1","price":1799,"available":true,"rating":4.8,"reviewCount":260,"manufacturer":{"manufacturerId":12,"name":"Lenovo"},"categories":[{"categoryId":30,"name":"Laptops"}],"suggest":{"input":["thinkpad","lenovo laptop"]}}

POST _bulk
{ "index": { "_index": "products", "_id": "2020" } }
{ "productId":2020,"name":"Sony WH-1000XM5 Headphones","sku":"SONYWH1000XM5","price":399,"available":true,"rating":4.8,"reviewCount":450,"manufacturer":{"manufacturerId":3,"name":"Sony"},"categories":[{"categoryId":20,"name":"Headphones"}],"suggest":{"input":["sony headphones"]}}
{ "index": { "_index": "products", "_id": "2021" } }
{ "productId":2021,"name":"Bose QuietComfort 45","sku":"BOSEQC45","price":349,"available":true,"rating":4.7,"reviewCount":390,"manufacturer":{"manufacturerId":13,"name":"Bose"},"categories":[{"categoryId":20,"name":"Headphones"}],"suggest":{"input":["bose headphones"]}}
{ "index": { "_index": "products", "_id": "2022" } }
{ "productId":2022,"name":"Apple AirPods Pro","sku":"AIRPODSPRO","price":249,"available":true,"rating":4.8,"reviewCount":600,"manufacturer":{"manufacturerId":1,"name":"Apple"},"categories":[{"categoryId":21,"name":"Earbuds"}],"suggest":{"input":["airpods","apple earbuds"]}}
{ "index": { "_index": "products", "_id": "2023" } }
{ "productId":2023,"name":"Samsung Galaxy Buds 2","sku":"GALAXYBUDS2","price":149,"available":true,"rating":4.6,"reviewCount":270,"manufacturer":{"manufacturerId":2,"name":"Samsung"},"categories":[{"categoryId":21,"name":"Earbuds"}],"suggest":{"input":["samsung earbuds"]}}

POST _bulk
{ "index": { "_index": "products", "_id": "2030" } }
{ "productId":2030,"name":"Apple Watch Ultra","sku":"APPLEWATCHULTRA","price":799,"available":true,"rating":4.9,"reviewCount":200,"manufacturer":{"manufacturerId":1,"name":"Apple"},"categories":[{"categoryId":50,"name":"Smart Watches"}],"suggest":{"input":["apple watch"]}}
{ "index": { "_index": "products", "_id": "2031" } }
{ "productId":2031,"name":"Samsung Galaxy Watch 6","sku":"GALAXYWATCH6","price":399,"available":true,"rating":4.6,"reviewCount":240,"manufacturer":{"manufacturerId":2,"name":"Samsung"},"categories":[{"categoryId":50,"name":"Smart Watches"}],"suggest":{"input":["galaxy watch"]}}

POST _bulk
{ "index": { "_index": "products", "_id": "2040" } }
{ "productId":2040,"name":"Logitech MX Master 3 Mouse","sku":"LOGIMX3","price":129,"available":true,"rating":4.8,"reviewCount":520,"manufacturer":{"manufacturerId":6,"name":"Logitech"},"categories":[{"categoryId":60,"name":"Accessories"}],"suggest":{"input":["logitech mouse"]}}
{ "index": { "_index": "products", "_id": "2041" } }
{ "productId":2041,"name":"Razer Gaming Mouse","sku":"RAZERGM","price":99,"available":true,"rating":4.5,"reviewCount":180,"manufacturer":{"manufacturerId":14,"name":"Razer"},"categories":[{"categoryId":60,"name":"Accessories"}],"suggest":{"input":["razer mouse"]}}
{ "index": { "_index": "products", "_id": "2042" } }
{ "productId":2042,"name":"Keychron K8 Keyboard","sku":"KEYCHRONK8","price":109,"available":true,"rating":4.7,"reviewCount":300,"manufacturer":{"manufacturerId":7,"name":"Keychron"},"categories":[{"categoryId":60,"name":"Accessories"}],"suggest":{"input":["keychron keyboard"]}}
# kodex
유사 발음 검색을 위한 Kodex 한글 토큰 필터 입니다.<br>

# Getting started 
```bash
git clone https://github.com/eunsour/kodex.git
./gradlew clean assemble
```
<br>

1. 예제
유사발음 검색
``` javascript
# 인덱스 생성
PUT kodex_test
{
  "settings": {
    "analysis": {
      "analyzer": {
        "kodex_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "eunsour_kodex"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "keyword",
        "copy_to": ["name_kodex"]
      },
      "name_kodex": {
        "type": "text",
        "analyzer": "kodex_analyzer"
      }
    }
  }
}


# kodex 애널라이저로 텍스트 분석
GET kodex_test/_analyze
{
  "analyzer": "kodex_analyzer",
  "text": "엘라스틱 서치"  
}
```

```javascript
# kodex 애널라이저로 텍스트 분석 결과
{
  "tokens" : [
    {
      "token" : "ㅇ44731",
      "start_offset" : 0,
      "end_offset" : 4,
      "type" : "<HANGUL>",
      "position" : 0
    },
    {
      "token" : "ㅅ3",
      "start_offset" : 5,
      "end_offset" : 7,
      "type" : "<HANGUL>",
      "position" : 1
    }
  ]
}
```

```javascript
# 예제 데이터 색인
POST /_bulk
{ "index" : { "_index" : "kodex_test", "_id" : "1" } }
{ "name" : "아웃" }
{ "index" : { "_index" : "kodex_test", "_id" : "2" } }
{ "name" : "아우트" }
{ "index" : { "_index" : "kodex_test", "_id" : "3" } }
{ "name" : "애드" }
{ "index" : { "_index" : "kodex_test", "_id" : "4" } }
{ "name" : "메소드" }
{ "index" : { "_index" : "kodex_test", "_id" : "5" } }
{ "name" : "마스터" }
{ "index" : { "_index" : "kodex_test", "_id" : "6" } }
{ "name" : "휠라" }
{ "index" : { "_index" : "kodex_test", "_id" : "7" } }
{ "name" : "힐라" }
{ "index" : { "_index" : "kodex_test", "_id" : "8" } }
{ "name" : "필라" }
{ "index" : { "_index" : "kodex_test", "_id" : "9" } }
{ "name" : "휘울라" }
{ "index" : { "_index" : "kodex_test", "_id" : "10" } }
{ "name" : "에이서" }
{ "index" : { "_index" : "kodex_test", "_id" : "11" } }
{ "name" : "유저" }
{ "index" : { "_index" : "kodex_test", "_id" : "12" } }
{ "name" : "아시아" }


# 예제 텍스트 검색 
GET kodex_test/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "match": {
              "name_kodex": "힐라"
            }
          }
      ]
    }
  }
}
```

```javascript
# 검색 결과
{
  "took" : 0,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 3,
      "relation" : "eq"
    },
    "max_score" : 1.3121864,
    "hits" : [
      {
        "_index" : "kodex_test",
        "_type" : "_doc",
        "_id" : "6",
        "_score" : 1.3121864,
        "_source" : {
          "name" : "휠라"
        }
      },
      {
        "_index" : "kodex_test",
        "_type" : "_doc",
        "_id" : "7",
        "_score" : 1.3121864,
        "_source" : {
          "name" : "힐라"
        }
      },
      {
        "_index" : "kodex_test",
        "_type" : "_doc",
        "_id" : "9",
        "_score" : 1.3121864,
        "_source" : {
          "name" : "휘울라"
        }
      }
    ]
  }
}
```

## To Do
1. EKODEX 개발 및 테스트

## 참고자료
- https://www.koreascience.or.kr/article/CFKO200708355727849.pdf

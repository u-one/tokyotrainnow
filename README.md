# tokyotrainnow

![build and deploy](https://github.com/u-one/tokyotrainnow/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/u-one/tokyotrainnow/branch/main/graph/badge.svg?token=IXTGKP5JMM)](https://codecov.io/gh/u-one/tokyotrainnow)

[![Maintainability](https://api.codeclimate.com/v1/badges/9bace3b745e9746e043d/maintainability)](https://codeclimate.com/github/u-one/tokyotrainnow/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/9bace3b745e9746e043d/test_coverage)](https://codeclimate.com/github/u-one/tokyotrainnow/test_coverage)

東京を中心とした鉄道路線ごとの現在の列車位置を表示するWebアプリケーション実装

* Spring Boot
* Spring MVC
* Thymeleaf

### 利用データ
* [公共交通オープンデータセンター](https://developer-dc.odpt.org/)

## 実行方法

### ローカル実行
#### 環境変数
```
SPRING_PROFILES_ACTIVE=dev
NET_UONEWEB_TOKYOTRAINNOW_ODPTAPI_KEY=[odpt api key]
NET_UONEWEB_TOKYOTRAINNOW_ODPTAPI_ENDPOINT=[odpt api endpoint]
```
```shell
./gradlew bootRun
```

### smokeテスト
* ローカルで起動したアプリケーションにリクエスト、APIのレスポンスが想定通りであることをテスト
* 裏では実際のODPT APIにリクエスト

#### 環境変数
```
SPRING_PROFILES_ACTIVE=smoke-test
NET_UONEWEB_TOKYOTRAINNOW_ODPTAPI_KEY=[odpt api key]
NET_UONEWEB_TOKYOTRAINNOW_ODPTAPI_ENDPOINT=[odpt api endpoint]
```

#### 実行
```shell
./gradlew smokeTest
```
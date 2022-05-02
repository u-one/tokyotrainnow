package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/api/current-railway'
        body()
        headers {
            contentType('application/json')
        }
    }
    response {
        status OK()
        body([
               "title": "浅草線",
                "lineCode": "A",
                "color": "#FF535F",
                "operator": "東京都交通局",
                "sections": [
                       "title": "",
                        "stationId": "",
                        "stationCode": "A-01"
                ]
        ])
        headers {
            contentType('application/json')
        }
    }
}
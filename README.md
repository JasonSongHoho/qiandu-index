千度索引建立模块 - 索引后台建立的一个简单java主函数实现.

查看 [qiandu-index](http://10.110.13.136:9200/_plugin/head/) 的相关数据.

## 作者

 * 姓名:: 浪潮集团
 * 地址:: http://www.inspur.com 

## 特点

- 索引数据格式进行简单的处理即可转换为千度数据模型
- 索引本身支持以下特性：
    1. 地理搜索
    2. 拼音建议为相应汉字
    3. 时间范围搜索
    4. 图片（base64）、ID等信息不可搜索

## 数据结构

###  索引结构和数据模型的异同

- 索引结构增加索引时间字段
如：
 ```
   "indexTime": "2016-09-22 22:52:44"
 ```
 
- 索引结构采用Elasticsearch方式存储地理坐标信息
如：
 ```
 "location": {
         "lon": "117.229024",
         "lat": "36.50508"
     }
```
    而千度数据模型采用类似以下属性存储地理坐标信息
如：
```
     "latitude":"36.67264",
     "longitude":"117.133608"
```

- 索引结构增加搜索建议字段
如：
```
 "suggestField": {
        "input": [
            "王哲",
            "济南市历城区仲宫镇西营镇王村",
            "370105979052245122"
        ]
    }
```

- 索引结构统一对象属性数据格式
dataFormat统一为R类型。S类型可直接转为R类型，C类型转为R类型需要拼接个子属性的propertyNote和propertyData属性值.
如：
```
"dataFormat": "R",
"propertyValue": {
                "propertyNote": "区域详细地址",
                "propertyData": "济南市历城区仲宫镇西营镇王村",
                "valueType": "S",
                "propertyURI": "data.property.address"
            }
```


## 附录
1. 索引数据结构
索引中的数据结构如下：
```
{
    "mediaSet": [
        {
            "thumbnail": [
                {
                    "thumbnailData": "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"
                }
            ],
            "mediaTitle": "媒体的默认标题",
            "mediaData": "媒体的数据",
            "mediaDescription": "媒体的默认详细描述信息.",
            "mediaShortDescription": "媒体的默认简短描述信息.",
            "dataSourceRecordSet": [
                {
                    "dataSource": "default_ds1",
                    "importKey": "default_media_id"
                }
            ]
        }
    ],
    "title": "370105979052245122",
    "indexTime": "2016-09-22 22:52:44",
    "location": {
        "lon": "117.229024",
        "lat": "36.50508"
    },
    "noteSet": [
        {
            "noteData": "说明的默认描述信息",
            "noteTitle": "说明的默认标题",
            "dataSourceRecordSet": [
                {
                    "dataSource": "default_ds1",
                    "importKey": "default_note_id"
                }
            ]
        }
    ],
    "suggestField": {
        "input": [
            "王哲",
            "济南市历城区仲宫镇西营镇王村",
            "370105979052245122"
        ]
    },
    "type": "data.object.entities.person",
    "primaryObject": "74",
    "propertySet": [
        {
            "propertyValue": {
                "propertyNote": "人员身份号码",
                "propertyData": "370105979052245122",
                "valueType": "S",
                "propertyURI": "data.property.idnumber"
            },
            "dataFormat": "R",
            "type": "data.property.idnumber",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 343
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "姓名",
                "propertyData": "王哲",
                "valueType": "S",
                "propertyURI": "data.property.name"
            },
            "dataFormat": "R",
            "type": "data.property.name",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 344
                },
                {
                    "dataSource": "ds1",
                    "importKey": 345
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "性别",
                "propertyData": "男",
                "valueType": "S",
                "propertyURI": "data.property.sexy"
            },
            "dataFormat": "R",
            "type": "data.property.sexy",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 346
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "民族",
                "propertyData": "汉",
                "valueType": "S",
                "propertyURI": "data.property.nation"
            },
            "dataFormat": "R",
            "type": "data.property.nation",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 347
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "文化程度",
                "propertyData": "小学",
                "valueType": "S",
                "propertyURI": "data.property.edu"
            },
            "dataFormat": "R",
            "type": "data.property.edu",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 348
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "出生日期",
                "propertyData": "1979-05-22 00:00:00.0",
                "valueType": "T",
                "propertyURI": "data.property.birthdate"
            },
            "dataFormat": "R",
            "type": "data.property.birthdate",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 349
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "婚姻状况",
                "propertyData": "是",
                "valueType": "N",
                "propertyURI": "data.property.married"
            },
            "dataFormat": "R",
            "type": "data.property.married",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 350
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "联系电话",
                "propertyData": "13122318677",
                "valueType": "S",
                "propertyURI": "data.property.telephone"
            },
            "dataFormat": "R",
            "type": "data.property.telephone",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 351
                }
            ]
        },
        {
            "propertyValue": {
                "propertyNote": "区域详细地址",
                "propertyData": "济南市历城区仲宫镇西营镇王村",
                "valueType": "S",
                "propertyURI": "data.property.address"
            },
            "dataFormat": "R",
            "type": "data.property.address",
            "dataSourceRecordSet": [
                {
                    "dataSource": "ds1",
                    "importKey": 352
                },
                {
                    "dataSource": "ds1",
                    "importKey": 353
                }
            ]
        }
    ]
}
```

2. 千度数据模型
请查看[千度数据模型](http://note.youdao.com/share/?id=019904c1cb2c35f7e5a21578679122d6&type=note#/)详细信息.

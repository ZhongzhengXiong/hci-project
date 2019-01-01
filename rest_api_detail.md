* **/users**

  **GET** :

  返回用户的公共信息，公共信息结构：

  {
  ​    "name": "tomcat",
  ​    "id": 89218489,
  ​    "avatar": "89218489.jpg"
  }

  **POST**:

   创建一个新用户，发送的是表格数据，表格数据共两个字段

  |     key     | Value                                                        |
  | :---------: | :----------------------------------------------------------- |
  | registerReq | {<br/>      "name":"hahah",<br/>      "password":"Xiong@123",<br/>      "email":"zzxiong19@fudan.edu.cn",<br/>      "phone":"15201723157"<br/>} |
  |   avatar    | 头像图片                                                     |

    返回：

    {
    ​    "name": "api_user",
    ​    "id": 70659220,
    ​    "avatar": "70659220.jpg",
    ​    "email": "zzxiong19@fudan.edu.cn",
    ​    "phone": "15201723158"
    }



* **/users/{uid}**

  **GET** 获取指定id的用户公共信息，返回的用户公共信息结构和/users中相同

* **/users/{uid}/avatar**

  **GET** 下载用户的头像照片，返回的是一个图片文件，文件名在用户公共信息结构中

* **/account**

  **GET** 获取登陆用户的所有信息，需在在头部Authorization字段放入登录时返回的token

  数据结构如下：

  {

  ​    "name": "tomcat",
  ​    "id": 89218489,
  ​    "avatar": "89218489.jpg",
  ​    "email": "zzxiong15@fudan.edu.cn",
  ​    "phone": "15201723156"

  }

  （比用户公共信息多了email和phone数据）

* **/account/messages**

  **GET**（头部Authorization字段放入token），返回用户的所有信息

  [

  ​	{
  ​        	"messageId": 73247173,
  ​        	"content": "hahah joined in the activity (name: UNO) just now. Welcome!",
  ​       		 "date": "2018-12-31 12:36",
  ​        	 "activityName": "UNO",
  ​        	 "activityId": 57262540
  ​    	},

  ​	{

  ​		...

  ​	}

  ]

* **/account/activities**

  **GET** (Authorization),返回用户所参加（包括创建）的所有活动信息

  [

   {
  ​        "activityId": 76776626,
  ​        "place": "Fudan University",
  ​        "name": "UNO",
  ​        "beginDate": "2018-12-30 08:00",
  ​        "endDate": "2018-12-30 16:20",
  ​        "status": true,
  ​        "introPhotoName": "76776626.jpeg"
  ​    },

  ...

  ]

* **/account/stat-info**

  **GET** 获取统计信息（Authorization）

  返回

  {
  ​    "participateNum": 9,
  ​    "createNum": 9,
  ​    "activityTypeStats": {
  ​        "PARTY": 8
  ​    },
  ​    "placeSet": [
  ​        "Fudan University"
  ​    ],
  ​    "reviewNum": 0,
  ​    "uploadPhotoNum": 0
  }



* **/activities**

  **GET** 获取所有活动公开信息，返回值入下，活动公开信息列表

  [
  ​    {
  ​        "activityId": 99046231,
  ​        "place": "Fudan University",
  ​        "name": "UNO",
  ​        "beginDate": "2018-12-30 08:00",
  ​        "endDate": "2018-12-30 16:20",
  ​        "status": true,
  ​        "introPhotoName": "99046231.jpeg"
  ​    },
  ​    {
  ​        "activityId": 20097053,
  ​        "place": "Fudan University",
  ​        "name": "UNO",
  ​        "beginDate": "2018-12-30 08:00",
  ​        "endDate": "2018-12-30 16:20",
  ​        "status": true,
  ​        "introPhotoName": "20097053.jpg"
  ​    },

  ...

  ]

  **POST** (Authorization) 当前用户创建一个新活动

   发送的是表单形式的数据

  | Key               | Value                                                        |
  | ----------------- | ------------------------------------------------------------ |
  | createActivityReq | {<br/> "place": "Fudan University",
 "name": "Shamorock Competition",
  "detail": "The Software Competition held by Fudan University software school.",
 "beginDate":"2018-12-31 10:00",
"endDate":"2018-12-31 18:20",
"userLimit": 100,
 "type": "PARTY"
} |
  | introPhoto        | 介绍图片                                                     |

    返回：

    {
    ​    "activityId": 37754217,
    ​    "place": "Fudan University",
    ​    "name": "Shamorock Competition",
    ​    "beginDate": "2018-12-31 10:00",
    ​    "endDate": "2018-12-31 18:20",
    ​    "status": true,
    ​    "introPhotoName": "37754217.jpg",
    ​    "detail": "The Software Competition held by Fudan University software school.",
    ​    "userLimit": 100,
    ​    "participatorIds": [
    ​        89218489
    ​    ],
    ​    "creatorId": 89218489,
    ​    "invitingCode": "513401"
    }

* **/activities/{aid}**

  **GET** 指定活动信息（Authorization）

  返回

  {
  ​    "activityId": 46791770,
  ​    "place": "Fudan University",
  ​    "name": "UNO",
  ​    "beginDate": "2018-12-30 08:00",
  ​    "endDate": "2018-12-30 16:20",
  ​    "status": true,
  ​    "introPhotoName": "46791770.jpg",
  ​    "detail": "The First UNO Table Games held by Fudan University Table Games Association.",
  ​    "userLimit": 50,
  ​    "participatorIds": [
  ​        89218489
  ​    ],
  ​    "creatorId": 89218489,
  ​    "invitingCode": "866515"
  }

  **PATCH** （Authorization）创建者更新活动状态（比如关闭活动发送status false）

    发送：

    {
    ​    "status": false
    }

    返回：

    {
    ​    "activityId": 97451710,
    ​    "status": false
    }

* **/activities/{aid}/users**

  **GET** （Authorization）获取指定activity下所有用户

  返回数据

  [
  ​    {
  ​        "name": "hahah",
  ​        "id": 75960984,
  ​        "avatar": "75960984.png"
  ​    },
  ​    {
  ​        "name": "tomcat",
  ​        "id": 89218489,
  ​        "avatar": "89218489.jpg"
  ​    },
  ​    {
  ​        "name": "api_user",
  ​        "id": 70659220,
  ​        "avatar": "70659220.jpg"
  ​    }
  ]

  **POST** 当前用户（由Authorization中的token判断）加入该活动

  返回活动的详细信息

   {
    ​    "activityId": 57262540,
    ​    "place": "Fudan University",
    ​    "name": "UNO",
    ​    "beginDate": "2018-12-30 08:00",
    ​    "endDate": "2018-12-30 16:20",
    ​    "status": true,
    ​    "introPhotoName": "57262540.jpg",
    ​    "detail": "The First UNO Table Games held by Fudan University Table Games Association.",
    ​    "userLimit": 50,
    ​    "participatorIds": [
    ​        75960984,
    ​        89218489,
    ​        70659220
    ​    ],
    ​    "creatorId": 89218489,
    ​    "invitingCode": "217808"

  }


* **/activities/{aid}/notices**

  **GET** (Authorization) 获取activity下所有通知

  返回

  [
  ​    {
  ​        "noticeId": 89725093,
  ​        "content": "test of notice creation",
  ​        "title": "Notice Test",
  ​        "date": "2019-01-01 10:49"
  ​    },
  ​    ...
  ]

  **POST** （Authorization）创建一条新通知

    发送：

    {
    ​	"title": "Notice Test",
    ​	"content": "test of notice creation"
    }

    返回：

    {
    ​    "noticeId": 56606855,
    ​    "content": "test of notice creation",
    ​    "title": "Notice Test",
    ​    "date": "2019-01-01 10:52"
    }

* **/activities/{aid}/reviews**

  **GET**(Authorization) 获取该活动下所有用户评论

  返回：

  [
  ​    {
  ​        "reviewId": 54954235,
  ​        "userId": 89218489,
  ​        "content": "test for reviews creation",
  ​        "date": "2019-01-01 11:05"
  ​    },
  ​    {
  ​        "reviewId": 28152489,
  ​        "userId": 89218489,
  ​        "content": "test for reviews creation once again",
  ​        "date": "2019-01-01 11:06"
  ​    },

  ​    ...

  ]

  **POST**(Authorization)创建一条新评论

    发送：

    {

    ​	"content":"test for reviews creation once again"

    }

    返回：

    {
    ​    "reviewId": 28152489,
    ​    "userId": 28152489,
    ​    "content": "test for reviews creation once again",
    ​    "date": "2019-01-01 11:06"
    }





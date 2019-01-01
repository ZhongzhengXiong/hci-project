REST API

|                               | GET                            | POST                            | PUT                  | DELETE                                 | PATCH        |
| ----------------------------- | ------------------------------ | ------------------------------- | -------------------- | -------------------------------------- | ------------ |
| /users                        |                                | 创建一个用户                    |                      |                                        |              |
| /users/{uid}                  | 获取指定id的用户公共信息       |                                 |                      |                                        |              |
| /users/{uid}/avatar           | 获取用户头像                   |                                 |                      |                                        |              |
| /account                      | 获取登陆用户的所有信息         |                                 | 修改当前帐号用户信息 |                                        |              |
| /account/messages             | 获取登陆用户的所有消息         |                                 |                      |                                        |              |
| /account/messages/{mid}       |                                |                                 |                      | 删除某条通知                           |              |
| /account/activities           | 获取登陆用户的所有活动基础信息 |                                 |                      |                                        |              |
| /account/photos               | 获取用户上传的所有照片信息     |                                 |                      |                                        |              |
| /account/photo/{pid}          |                                |                                 |                      | 删除某张照片                           |              |
| /account/stat-info            | 获取用户统计信息               |                                 |                      |                                        |              |
| /activities                   | 获取所有活动公共信息           | 创建一个新活动                  |                      |                                        |              |
| /activities/{aid}             | 获取指定活动信息               |                                 | 更新某项活动         |                                        | 更新活动状态 |
| /activities/{aid}/users       | 获取指定activity下的所有用户   | 当前用户加入该活动              |                      | 删除指定activity下所有用户（除创建者） |              |
| /activities/{aid}/users/{uid} |                                |                                 |                      | 将指定用户踢出该活动                   |              |
| /activities/{aid}/notices     | 获取activity下的所有通知       | 在指定activity下创建一条新通知  |                      |                                        |              |
| /activities/{aid}/reviews     | 获取activity下所有评论         | 在指定activity下创建一条评论    |                      |                                        |              |
| /activities/{aid}/photos      | 获取该activit下所有的照片信息  | 在该activity下上传一张新照片    |                      |                                        |              |
| /activities/{aid}/introphoto  | 获取该活动的介绍照片           |                                 |                      |                                        |              |
| /token                        |                                | 用户登陆，返回一个新创建的token |                      | 用户登出，删除当前token                |              |
| /photo/{pid}                  | 下载某张图片                   |                                 |                      |                                        |              |


# 个人博客API文档

## 0. 一些类型和说明

这些类型是后面定义接口时会需要用到的. 采用类似于typescript的伪码写成.
```typescript
type URL = string

// 即用户, 即注册用户. 无论在用例中的身份如何, 都叫它Blogger.
type BloggerInfo = {
    id:         long
    name:       string
    avatarUrl:  string
    contact:    string  // 手机号
    email:      string
    graduate:   string  // 毕业院校
}

// 参考个人博客主页设计图. 这里描述了图里的一些信息
type BlogInfo = {
    blogger:    BloggerInfo
    visitCount: long    // 此个人主页被访问的次数
    likeCount:  int
    fans:       int     // 当前用户有多少人关注(follow)

    blogCount:  int
    blogsUrl:   URL
}

type PostInfo = {
    id:         long

    title:      string
    content:    string			// 帖子的简介
    detail:     URL 			// 帖子的具体内容

    author:     BloggerInfo 
    tags:       TagInfo[]
    avatar:     URL | null
    comments:   URL

    permission: {
        isPublic:           boolean // 是否公开
        needReviewComment:  boolean // 是否需要审核评论
    }
    visits:             long    // 博客被访问的次数
    likes:              int     // 被多少人喜欢过
    unreviewedCount:    int     // 没有审核过的评论的数量
}

type TagInfo = {
    id:             long
    name:           string
    owner:          BloggerInfo
    description:    string
}

type CommentInfo = {
    id:             long
    author:         BloggerInfo
    content:        string
    post:           URL 
    replyTo:        CommentInfo | null
    reviewPassed:   boolean
}

type AttachmentInfo = {
    id:             long
    name:           string  // 文件名
    suffix:			string	// 文件后缀名
    url:            URL
    owner:          BloggerInfo
    filesize:       long     // 以B为单位
}
```
在下面的表格中, 会提到返回值. 但它不是直接返回的. 而是下面的类型(伪码):
```typescript
type Result<T> = { code: int } & ({content: T} | { reason: string })
```
如, 如果返回值类型是long, 那一次成功请求的返回值可能是:
```json
{
    "code": 0,  
    "content": 6308
}
```
> 0表示此次返回没有问题. 这是在数据中的, 不是"404", "500"这种错误码 

或者, 有些请求没有返回值, 那它可能长这样(成功时): `{ "code": 0 }`

一次失败的请求可能是这样的:
```json
{
    "code": 1,
    "reason": "user not found"
}
```
| 错误码 | 原因           |
| ------ | -------------- |
| -1     | 未知错误       |
| 0      | 访问成功       |
| 1      | 未登录         |
| 2      | 没有访问权限   |
| 3      | 查询对象不存在 |
| 4      | 注册/登录失败  |



## 1. 登录注册

| 方法 | 接口      | 参数                                                           | 返回 | 描述 |
|------|-----------|----------------------------------------------------------------|------|------|
| POST | /register | name: string, password: string, contact: string, email: string | -    | 注册 |
| POST | /login    | contact: string, password: string                              | -    | 登录 |

## 2. 博客管理

> 这一部分主要是在个人主页界面中会用到的. 阅读时最好参考"需求分析-概要.pdf"中个人主页部分的设计图.
> 
> 对于是否暴露隐私信息, 这取决于调用接口时的用户登录情况. 这都是后端处理的. 

### 2.1 博客管理

> 按理说博主(Blogger)和博客(Blog)是一对一的关系: 一个博主只管理它自己的博客, 
> 但这里还是拆成两个对象了. 这有两个原因: 
>
> 1. **博客**中的数据经常变化, 而博主的数据不是. 分开能方便数据库的设计, 也许也能优化性能...?; 
> 2. 方便归类. 像标签,附件等功能归给**博主**, 博文等归给**博客**.

| 方法  | 接口             | 参数                    | 返回        | 描述                           |
| ----- | ---------------- | ----------------------- | ----------- | ------------------------------ |
| GET   | /blogger/self    | -                       | BloggerInfo | 查询当前登录用户的个人简介     |
| GET   | /blogger/{uid}   | -                       | BloggerInfo | 查询个人简介(不包括博客的信息) |
| PATCH | /blogger/{uid}   | info: BloggerInfo       | -           | 修改个人简介                   |
| GET   | /blog/{bid}      | -                       | BlogInfo    | 查看个人博客数据               |
| GET   | /blog/{bid}/post | page: int, perpage: int | PostInfo[]  | 查看个人全部博文               |

### 2.2 标签和附件

| 方法   | 接口                            | 参数                     | 返回             | 描述             |
| ------ | ------------------------------- | ------------------------ | ---------------- | ---------------- |
| GET    | /public/tag/                    | page: int, perpage: int  | TagInfo[]        | 查询公开标签     |
| GET    | /blogger/{uid}/tag              | page: int, perpage: int  | TagInfo[]        | 查询某用户的标签 |
| GET    | /blogger/{uid}/tag/{tid}        | -                        | TagInfo          | 获取标签信息     |
| POST   | /blogger/{uid}/tag              | tag: TagInfo             | long             | 添加标签         |
| PATCH  | /blogger/{uid}/tag/{tid}        | tag: TagInfo             | -                | 修改标签信息     |
| DELETE | /blogger/{uid}/tag/{tid}        | -                        | -                | 删除标签         |
| GET    | /blogger/{uid}/attachment       | page: int, perpage: int  | AttachmentInfo[] | 查询附件列表     |
| GET    | /blogger/{uid}/attachment/{aid} | -                        | AttachmentInfo   | 获取附件信息     |
| POST   | /blogger/{uid}/attachment       | name: string, file: File | long             | 上传附件         |
| DELETE | /blogger/{uid}/attachment/{aid} | -                        | -                | 删除附件         |

## 3. 博文管理

| 方法   | 接口                          | 参数           | 返回     | 描述                 |
| ------ | ----------------------------- | -------------- | -------- | -------------------- |
| POST   | /blog/{bid}/post              | post: PostInfo | long     | 添加博客             |
| GET    | /blog/{bid}/post/{pid}        |                | PostInfo | 获取某博文           |
| GET    | /blog/{bid}/post/{pid}/detail | -              | String   | 返回博文详细具体内容 |
| PATCH  | /blog/{bid}/post/{pid}        | post: PostInfo | -        | 修改博客             |
| PATCH  | /blog/{bid}/post/{pid}/detail | detail: String | -        | 修改博文详细具体内容 |
| DELETE | /blog/{bid}/post/{pid}        |                | -        | 删除博客             |

## 4. 评论

| 方法   | 接口                                        | 参数                                  | 返回          | 描述                                                         |
| ------ | ------------------------------------------- | ------------------------------------- | ------------- | ------------------------------------------------------------ |
| GET    | /blog/{bid}/post/{pid}/comment              | all: boolean, page: int, perpage: int | CommentInfo[] | 返回评论列表; all设置为false时只返回未审核的评论. 只能查出非回复的评论 |
| POST   | /blog/{bid}/post/{pid}/comment              | comment: CommentInfo, reply: long     | -             | 添加评论, reply指对另一个ID为`reply`的评论的回复             |
| DELETE | /blog/{bid}/post/{pid}/comment/{cid}        |                                       | -             | 删除评论                                                     |
| GET    | /blog/{bid}/post/{pid}/comment/{cid}/reply  | page: int, perpage: int               | CommentInfo[] | 获取当前评论的所有回复                                       |
| POST   | /blog/{bid}/post/{pid}/comment/{cid}/review | pass: boolean                         | -             | 审核评论                                                     |

## 5. 订阅

| 方法 | 接口                       | 参数 | 返回    | 描述                            |
| ---- | -------------------------- | ---- | ------- | ------------------------------- |
| POST | /blogger/{uid}/subsribe    | -    | -       | 订阅博主                        |
| POST | /blooger/{uid}/unsubscribe | -    | -       | 取消订阅                        |
| GET  | /blogger/{uid}/subsribed   | -    | boolean | 是否关注了某博主. uid为关注者的 |

## 6. 首页和帖子浏览详情页

| 方法 | 接口                    | 参数                         | 返回       | 描述                                |
| ---- | ----------------------- | ---------------------------- | ---------- | ----------------------------------- |
| POST | /public/post            | page: int, perpage: int      | PostInfo[] | 按照时间/推荐, 返回在首页展示的博文 |
| GET  | /public/search          | tags: string[], info: string | PostInfo[] | 搜索帖子                            |
| POST | /public/post/{pid}/like |                              | -          | 喜欢博文                            |


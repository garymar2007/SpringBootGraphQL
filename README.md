## Sping Boot GraphQL

This module contains development notes about GraphQL.

## Introduction

GraphQL is a relatively new concept from Facebook, billed as an alternative to REST for Web APIs.

Traditional REST APIs work with the concept of Resources that the server manages. We can manipulate 
these resources in some standard ways, following the various HTTP verbs. This works very well as long as our API 
fits the resource concept but quickly falls apart when we need to deviate from it.
This also suffers when the client needs data from multiple resources simultaneously, such as requesting a blog post 
and comments. Typically, this is solved by having the client make multiple requests or having the server supply 
extra data that might not always be required, leading to larger response sizes.

GraphQL offers a solution to both of these problems. It allows the client to specify exactly what data it desires, 
including navigating child resources in a single request and allows for multiple queries in a single request.

## Example

```a blog
query {
  recentPosts(count: 10, offset: 0) {
        id
        title
        category
        author {
            id
            name
            thumbnail
        }
    }
}
```
In traditional REST API, this either needs 11 requests or a single request that needs 
to include the author details in the post details.

## GraphQL Schema
The GraphQL server exposes a schema describing the API. This schema consists of type definitions. Each type has one 
or more fields, each taking zero or more arguments and returning a specific type.

An example GraphQL Schema for a blog may contain the following definitions describing a Post, the Author of the post, 
and a root query to get the most recent posts on the blog:
    
    ```schema
    type Post {
        id: ID!
        title: String!
        text: String!
        category: String!
        author: Author!
    }
    
    type Author {
        id: ID!
        name: String!
        thumbnail: String
        posts: [Post]!
    }

    # The Root Query for the application
    type Query {
        recentPosts(count: Int, offset: Int): [Post]!
    }

    # The Root Mutation for the application
    type Mutation {
        createPost(title: String!, text: String!, category: String, authorId: String!) : Post!
    }
    '''
    
    NB: ! means non-nullable type.

### GraphQL sample queries

Query
```shell script
curl \
--request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\n    recentPosts(count: 2, offset: 0) {\n        id\n        title\n        author {\n            id\n            posts {\n                id\n            }\n        }\n    }\n}"}'
```

Mutation
```shell script
curl \
--request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"mutation {\n    createPost(title: \"New Title\", authorId: \"Author2\", text: \"New Text\") {\n id\n       category\n        author {\n            id\n            name\n        }\n    }\n}"}'
```

### Relevant Articles:

- [Getting Started with GraphQL and Spring Boot](https://www.baeldung.com/spring-graphql)
- [Expose GraphQL Field with Different Name](https://www.baeldung.com/graphql-field-name)
- [Error Handling in GraphQL With Spring Boot](https://www.baeldung.com/spring-graphql-error-handling)
- [How to Test GraphQL Using Postman](https://www.baeldung.com/graphql-postman)
- [GraphQL vs REST](https://www.baeldung.com/graphql-vs-rest)
- [REST vs. GraphQL vs. gRPC – Which API to Choose?](https://www.baeldung.com/rest-vs-graphql-vs-grpc)
- [Implementing GraphQL Mutation Without Returning Data](https://www.baeldung.com/java-graphql-mutation-no-return-data)
import Api from "./Api";

class PostsApi {
  getAllPosts() {
    return Api.get("/posts");
  }

  getPostById(id) {
    return Api.get("/posts/" + id);
  }

  createPost(post) {
    return Api.post("/posts", post);
  }

  updatePost(postId, post) {
    return Api.put("/posts/" + postId, post);
  }

  deletePost(id) {
    return Api.delete("/posts/" + id);
  }
}

export default new PostsApi();

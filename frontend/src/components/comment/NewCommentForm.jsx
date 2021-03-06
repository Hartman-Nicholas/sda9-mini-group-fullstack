import React from "react";

export default function NewCommentForm({ onSubmit }) {
  const [body, setBody] = React.useState("");

  const handleSubmit = () => {
    // Invoke the passed in event callback
    onSubmit({
      body: body,
    });

    // Clear the input field
    setBody("");
  };

  //<NewPostForm onSubmit={(postData) => createPost(postData)} />;

  return (
    <form>
      <div>
        <div>
          <textarea
            placeholder="Your comment here"
            value={body}
            onChange={(e) => setBody(e.target.value)}
          />
        </div>
      </div>
      <div>
        <button className="btn" type="button" onClick={handleSubmit}>
          Comment
        </button>
      </div>
    </form>
  );
}

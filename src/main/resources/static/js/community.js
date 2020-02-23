function postComment() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    console.log(questionId);
    console.log(commentContent);
    submitComment(questionId,1,commentContent)
}

function submitComment(targetId,type,commentContent){
        if (!commentContent){
        alert("不能回复空！！");
        return;
    }
    if (!targetId){
        alert("您所回复的问题不存在！！");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            // "parentId":500,
            "content": commentContent,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=99c9d3cce8bc3668ba98&redirect_uri=http://127.0.0.1/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data");
    console.log(commentId);
    var content = $("#input-" + commentId).val();

    submitComment(commentId,2,content)
}

/***
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data");
    var commentIndexName = $("#comment-"+id);
    //获取二级评论的展开状态
    var collapseStatus = e.getAttribute("data-collapse");
    if(collapseStatus){
        //折叠二级评论
        commentIndexName.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("icon-question-comment-active");
    }else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length !== 1) {
            //添加标签，是的二级评论可以展开
            commentIndexName.addClass("in");
            //在e内部添加标签，标记出二级评论已经展开的状态
            e.setAttribute("data-collapse","in");
            e.classList.add("icon-question-comment-active");
        }else {
            $.getJSON( "/comment/" + id, function( data ) {

                var items = [];

                $.each( data.data.reverse(), function(key , comment ) {

                    var avatarImgElement= $("<img/>", {
                        "class":"media-object-demo img-rounded",
                        "src":comment.user.avatarUrl
                    });

                    var mediaLeftElement= $("<div/>", {
                        "class":"media-left"
                    }).append(avatarImgElement);

                    var mediaElement = $("<div/>", {
                        "class":"media"
                    }).append(mediaLeftElement);

                    var username = $("<h5/>", {
                        "class":"media-heading",
                        "html":comment.user.name
                    });

                    var commentContent = $("<div/>", {
                        "html":comment.content}
                    );

                    var dateCreate = $("<div/>", {
                        "class":"menu-icon"
                    }).append($("<span/>", {
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-D')
                    }));

                    var mediaBody = $("<div/>", {
                        "class":"media-body"
                    }).append(username)
                        .append(commentContent)
                        .append(dateCreate);

                    mediaElement.append(mediaBody);

                    var commentElement = $("<div/>", {
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-list"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //添加标签，是的二级评论可以展开
                commentIndexName.addClass("in");
                //在e内部添加标签，标记出二级评论已经展开的状态
                e.setAttribute("data-collapse","in");
                e.classList.add("icon-question-comment-active");
            });
        }


    }

}
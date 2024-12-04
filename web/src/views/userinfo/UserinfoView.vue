<template>
    <ContentField>

        <div class="container" style="display: flex;">
            <div style="width: 18rem;">
                <img :src="photo" alt="">
                <div class="input-group mb-3" style="margin-top: 10px;">
                    <span class="input-group-text" id="basic-addon1">更换头像</span>
                    <input @keyup.enter="updatePhoto" type="text" class="form-control" placeholder="新的头像URL" aria-label="Username" aria-describedby="basic-addon1" v-model="newPhoto">
                    <button class="btn btn-primary" @click="updatePhoto">确定</button>
                  </div>
            </div>
            
            <div style="">
                <h4 style="font-style: italic; font-weight: 1000;">用户名</h4>
                <input @key.enter="updateUsername" v-model="username" type="text">
                <button class="btn btn-primary" @click="updateUsername">修改</button>
                <div class="error">{{ error_message }}</div>
            </div>

        </div>

            
        
    </ContentField>
    
</template>

<script>
import ContentField from '../../components/ContentField';
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from "jquery";

export default {
    components: {
        ContentField,
    },
    setup() {

        const store = useStore();
        let photo = ref(store.state.user.photo);
        let username = ref('');
        username.value = store.state.user.username;
        let newPhoto = ref('');

        let error_message = ref('');

        const updateUsername = () => {
            $.ajax({
                url: `${store.state.url.DomainName}/api/user/account/update/username/`,
                type: 'POST',
                data: {
                    username: username.value,
                    id: store.state.user.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        store.commit("updateUsername", username.value);
                    } else {
                        error_message = resp.error_message;
                    }
                },
            });
        }

        const updatePhoto = () => {

            console.log(newPhoto.value);
            $.ajax({
                url: `${store.state.url.DomainName}/api/user/account/update/photo/`,
                type: `POST`,
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    photo: newPhoto.value,
                    id: store.state.user.id,
                },
                success(resp) {
                    console.log(resp);
                    store.commit("updatePhoto", newPhoto);
                    photo.value = newPhoto.value;
                    
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        

        return {
            username,
            photo,
            error_message,
            updateUsername,
            newPhoto,
            updatePhoto,
        }
    }
}

</script>

<style>

img {
    border-radius: 50%;
    width: 50%;
}

.container{
    text-align: center;
    margin: 0 auto;
    justify-content: center;
}

.error {
    color: red;
}

</style>
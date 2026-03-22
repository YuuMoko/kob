<template>
    <ContentField>

        <div class="profile-page">
            <div class="row g-4 align-items-stretch">
                <div class="col-12 col-lg-4">
                    <div class="card shadow-sm h-100">
                        <div class="card-body">
                            <div class="d-flex align-items-center justify-content-between mb-3">
                                <div>
                                    <div class="fw-bold">Profile photo</div>
                                    <div class="text-muted small">Supports jpg, png, webp, and other images</div>
                                </div>
                                <span v-if="uploading" class="badge text-bg-primary">Uploading</span>
                            </div>

                            <div class="avatar-wrap mx-auto mb-3">
                                <img class="avatar" :src="previewPhoto || photo" alt="avatar">
                            </div>

                            <div class="mb-2">
                                <input class="form-control" type="file" accept="image/*" @change="onPhotoChange">
                                <div class="form-text" v-if="newPhoto">
                                    Selected: {{ newPhoto.name }} ({{ prettySize(newPhoto.size) }})
                                </div>
                            </div>

                            <div class="d-flex gap-2">
                                <button class="btn btn-primary flex-fill" @click="uploadPhoto" :disabled="!newPhoto || uploading">
                                    Upload photo
                                </button>
                                <button class="btn btn-outline-secondary" @click="clearSelectedPhoto" :disabled="!newPhoto || uploading">
                                    Clear
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12 col-lg-8">
                    <div class="card shadow-sm h-100">
                        <div class="card-body">
                            <div class="d-flex align-items-center justify-content-between mb-3">
                                <div>
                                    <div class="fw-bold">Account settings</div>
                                    <div class="text-muted small">Change display name and password</div>
                                </div>
                            </div>

                            <div v-if="alert.message" class="alert py-2" :class="alert.type === 'success' ? 'alert-success' : 'alert-danger'">
                                {{ alert.message }}
                            </div>

                            <div class="row g-3">
                                <div class="col-12">
                                    <label class="form-label">Username</label>
                                    <div class="input-group">
                                        <input class="form-control" v-model="username" type="text" placeholder="Enter new username" @keyup.enter="updateUsername">
                                        <button class="btn btn-outline-primary" @click="updateUsername" :disabled="updatingUsername">
                                            {{ updatingUsername ? 'Saving...' : 'Save' }}
                                        </button>
                                    </div>
                                    <div class="form-text">Up to 100 characters; must be unique.</div>
                                </div>

                                <div class="col-12"><hr class="my-2"></div>

                                <div class="col-12 col-md-6">
                                    <label class="form-label">Current password</label>
                                    <input class="form-control" v-model="oldPassword" type="password" placeholder="Enter current password" @keyup.enter="updatePassword">
                                </div>
                                <div class="col-12 col-md-6">
                                    <label class="form-label">New password</label>
                                    <input class="form-control" v-model="newPassword" type="password" placeholder="Enter new password" @keyup.enter="updatePassword">
                                </div>
                                <div class="col-12 col-md-6">
                                    <label class="form-label">Confirm new password</label>
                                    <input class="form-control" v-model="confirmedPassword" type="password" placeholder="Re-enter new password" @keyup.enter="updatePassword">
                                </div>
                                <div class="col-12 col-md-6 d-flex align-items-end">
                                    <button class="btn btn-warning w-100" @click="updatePassword" :disabled="updatingPassword">
                                        {{ updatingPassword ? 'Updating...' : 'Change password' }}
                                    </button>
                                </div>
                                <div class="col-12">
                                    <div class="form-text">Password up to 100 characters. You may need to sign in again after a successful change.</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
        let username = ref(store.state.user.username || '');

        let newPhoto = ref(null); // File
        let previewPhoto = ref('');
        let uploading = ref(false);

        let updatingUsername = ref(false);
        let updatingPassword = ref(false);

        let oldPassword = ref('');
        let newPassword = ref('');
        let confirmedPassword = ref('');

        let alert = ref({ type: 'success', message: '' });

        const setAlert = (type, message) => {
            alert.value = { type, message };
            if (message) {
                setTimeout(() => {
                    if (alert.value.message === message) alert.value.message = '';
                }, 3000);
            }
        };

        const updateUsername = () => {
            if (updatingUsername.value) return;
            updatingUsername.value = true;
            setAlert('success', '');
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
                        setAlert('success', 'Username updated');
                    } else {
                        setAlert('danger', resp.error_message);
                    }
                },
                error() {
                    setAlert('danger', 'Request failed');
                },
                complete() {
                    updatingUsername.value = false;
                }
            });
        }

        const onPhotoChange = (e) => {
            const files = e.target.files;
            if (files && files.length > 0) {
                newPhoto.value = files[0];
                try {
                    previewPhoto.value = URL.createObjectURL(newPhoto.value);
                } catch (err) {
                    previewPhoto.value = '';
                }
            } else {
                newPhoto.value = null;
                previewPhoto.value = '';
            }
        }

        const clearSelectedPhoto = () => {
            newPhoto.value = null;
            previewPhoto.value = '';
        }

        const uploadPhoto = () => {
            if (!newPhoto.value || uploading.value) return;
            uploading.value = true;
            setAlert('success', '');

            const formData = new FormData();
            formData.append("file", newPhoto.value);

            $.ajax({
                url: `${store.state.url.DomainName}/api/user/account/upload/photo/`,
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        store.commit("updatePhoto", resp.photo);
                        photo.value = resp.photo;
                        clearSelectedPhoto();
                        setAlert('success', 'Photo updated');
                    } else {
                        setAlert('danger', resp.error_message);
                    }
                },
                error() {
                    setAlert('danger', 'Upload failed');
                },
                complete() {
                    uploading.value = false;
                }
            });
        }

        const updatePassword = () => {
            if (updatingPassword.value) return;
            updatingPassword.value = true;
            setAlert('success', '');

            $.ajax({
                url: `${store.state.url.DomainName}/api/user/account/update/password/`,
                type: "POST",
                data: {
                    old_password: oldPassword.value,
                    new_password: newPassword.value,
                    confirmed_password: confirmedPassword.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        oldPassword.value = "";
                        newPassword.value = "";
                        confirmedPassword.value = "";
                        setAlert('success', 'Password changed');
                    } else {
                        setAlert('danger', resp.error_message);
                    }
                },
                error() {
                    setAlert('danger', 'Request failed');
                },
                complete() {
                    updatingPassword.value = false;
                }
            });
        }

        const prettySize = (bytes) => {
            if (bytes == null) return "";
            if (bytes < 1024) return `${bytes} B`;
            const kb = bytes / 1024;
            if (kb < 1024) return `${kb.toFixed(1)} KB`;
            const mb = kb / 1024;
            return `${mb.toFixed(1)} MB`;
        }
        

        return {
            username,
            photo,
            updateUsername,
            newPhoto,
            onPhotoChange,
            uploadPhoto,
            uploading,
            previewPhoto,
            clearSelectedPhoto,
            updatingUsername,
            oldPassword,
            newPassword,
            confirmedPassword,
            updatePassword,
            updatingPassword,
            alert,
            prettySize,
        }
    }
}

</script>

<style>

.profile-page{
    padding: 8px 4px;
}

.avatar-wrap{
    width: 160px;
    height: 160px;
    border-radius: 999px;
    overflow: hidden;
    border: 1px solid rgba(0,0,0,.1);
    box-shadow: 0 10px 24px rgba(0,0,0,.08);
    background: #fff;
}

.avatar{
    width: 100%;
    height: 100%;
    object-fit: cover;
}

</style>
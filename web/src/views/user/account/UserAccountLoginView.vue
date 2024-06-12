<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form>
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input type="password" class="form-control" id="exampleFormControlInput1" placeholder="请输入密码">
                    </div>
                    <div class="error-message"></div>
                    <button type="submit" class="btn btn-primary ">提交</button>

                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../../components/ContentField.vue'

export default{
    components: {
        ContentField
    }
}
</script>

<style scpoed>
button {
    width: 100%
}

div.error-message{
    color:red
}
</style>
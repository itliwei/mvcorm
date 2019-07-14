<template>
    <div class="${meta.name}-container">
        <el-col :span="24" class="toolbar">
            <el-form :inline="true" :model="${meta.queryName}"  size="mini">
            <#list meta.queryFields as field>
                <el-form-item :span="6" label="${field.label}">
                    <el-input v-model="${meta.queryName}.${field.name}" placeholder=""></el-input>
                </el-form-item>
            </#list>
            <el-form-item :span="6" >
                <el-button type="primary" v-on:click="getList" size="mini">查询</el-button>
            </el-form-item>
            </el-form>
        </el-col>
        <div>
            <el-button type="danger" @click="addVisible=true" size="mini">添加</el-button>
        </div>

        <el-table :data="tableData" highlight-current-row v-loading="listLoading" style="width: 100%;" size="mini">
            <el-table-column type="index" width="50"></el-table-column>
            <#list meta.voFields as field>
                <el-table-column prop="${field.name}" label="${field.label}"></el-table-column>
            </#list>
            <el-table-column
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="getInfo(scope.row.id)" type="text" size="mini">查看</el-button>
                    <el-button @click="updateDialog(scope.row)" type="text" size="mini">修改</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination  style="float:right" background
                        @current-change="handleCurrentChange"
                        layout="prev, pager, next"
                        :total="total">
        </el-pagination>

        <el-dialog title="添加" :visible.sync="addVisible" >
            <el-form :model="${meta.dtoName}Add" label-width="80px" ref="addForm" :rules="addFormRules" size="mini">
                <#list meta.dtoFields as field>
                    <el-form-item label="${field.label}" prop="${field.label}">
                        <el-input v-model="${meta.dtoName}Add.${field.name}" ></el-input>
                    </el-form-item>
                </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleClose('addForm')" size="mini">取 消</el-button>
                <el-button type="primary" @click="handlerSubmit('addForm')" size="mini">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="修改" :visible.sync="updateVisible" >
            <el-form :model="${meta.dtoName}Update" label-width="80px" ref="form" :rules="updateFormRules">
                <#list meta.dtoFields as field>
                    <el-form-item label="${field.label}" prop="${field.label}">
                        <el-input v-model="${meta.dtoName}Update.${field.name}" ></el-input>
                    </el-form-item>
                </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleClose('updateForm')" size="mini">取 消</el-button>
                <el-button type="primary" @click="handlerSubmit('updateForm')" size="mini">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    import { Message, MessageBox } from 'element-ui'
    import {getInfo,add,update,del,getList} from '@/api/${meta.name}'
    import Axios from  'axios'

    const tableData = []
    export default {
        name: '${meta.name}',
        data() {
            return {
                tableData,
                total : 0,
                addVisible: false,
                updateVisible: false,
                listLoading: false,
                ${meta.queryName}:{
                    <#list meta.queryFields as field>
                    ${field.name}:null,
                    </#list>
                    pageNumber: 1,
                    pageSize: 10,
                },
                ${meta.dtoName}Add:{
                    <#list meta.dtoFields as field>
                        ${field.name}:null,
                    </#list>
                },
                ${meta.dtoName}Update:{
                    <#list meta.dtoFields as field>
                        ${field.name}:null,
                    </#list>
                },
                addFormRules: {
//                    ownerCode: [
//                        {required: true, message: '不能为空', trigger: 'blur'},
//                    ],

                },
                updateFormRules: {
    //                    ownerCode: [
    //                        {required: true, message: '不能为空', trigger: 'blur'},
    //                    ],

                },
            }
        },

        created () {
            this.init()
        },

        methods:{
            init() {
                this.getList();
            },

            getList(){
                this.tableData = [];
                this.listLoading = true;
                getList(this.${meta.queryName}).then((res) => {
                    res.data.list.forEach(item => {
                        this.tableData.push(item)
                    })
                    this.total = res.data.total;
                    this.listLoading = false;
                });
            },

            getInfo(id){
                this.$router.push({path: '/${meta.name}/info',query: {id: id}})

            },

            handleCurrentChange(val) {
                this.${meta.queryName}.pageNumber = val;
                this.getList();
            },

            updateDialog(val){
                this.updateVisible = true;
            },

            handlerSubmit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid){
                        if (formName ==="updateForm"){
                            update(this.${meta.dtoName}Update).then(response => {
                                Message({
                                    message: "修改成功",
                                    type: 'success',
                                });
                            });
                            this.init();
                        }else if (formName === "addForm"){
                            add(this.${meta.dtoName}Add).then(response => {
                                Message({
                                    message: "添加成功",
                                    type: 'success',
                                });
                            });
                            this.init();
                        }

                    }
                });
            },

            handleClose(formName) {
                this.addVisible = false;
                this.updateVisible = false;
                this.$refs[formName].resetFields();//将form表单重置
                this.$refs[formName].clearValidate();//将form表单重置
            },

        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .applicaton {
    &-container {
         margin: 30px;
     }
    &-text {
         font-size: 30px;
         line-height: 46px;
     }
    }
</style>

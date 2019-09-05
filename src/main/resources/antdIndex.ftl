
<template>
    <a-card>
        <div :class="advanced ? 'search' : null">
            <a-form layout="horizontal">
                <div :class="advanced ? null: 'fold'">
                    <#list meta.queryFields as field>
                    <#if (field_index+1) % 3 == 0>
                    <a-row >
                    </#if>
                        <a-col :md="8" :sm="24" >
                            <a-form-item :labelCol="{span: 5}"
                                          :wrapperCol="{span: 18, offset: 1}" label="${field.label}">
                                <a-input placeholder="请输入" />
                            </a-form-item>
                        </a-col>
                        <a-col :md="8" :sm="24" >
                            <a-form-item
                                    label="使用状态"
                                    :labelCol="{span: 5}"
                                    :wrapperCol="{span: 18, offset: 1}"
                            >
                                <a-select placeholder="请选择">
                                    <a-select-option value="1">关闭</a-select-option>
                                    <a-select-option value="2">运行中</a-select-option>
                                </a-select>
                            </a-form-item>
                        </a-col>
                        <a-col :md="8" :sm="24" >
                            <a-form-item
                                    label="调用次数"
                                    :labelCol="{span: 5}"
                                    :wrapperCol="{span: 18, offset: 1}"
                            >
                                <a-input-number style="width: 100%" placeholder="请输入" />
                            </a-form-item>
                        </a-col>
                    <#if (field_index+1) % 3 == 0>
                    </a-row>
                    </#if>
                    </#list>
                </div>
                <span style="float: right; margin-top: 3px;">
          <a-button type="primary">查询</a-button>
          <a-button style="margin-left: 8px">重置</a-button>
          <a @click="toggleAdvanced" style="margin-left: 8px">
            {{advanced ? '收起' : '展开'}}
            <a-icon :type="advanced ? 'up' : 'down'" />
          </a>
        </span>
            </a-form>
        </div>
        <div>
            <div class="operator">
                <a-button @click="addNew" type="primary">新建</a-button>
            </div>
            <standard-table
                    :columns="columns"
                    :dataSource="dataSource"
                    :selectedRows="selectedRows"
                    @change="onchange"
            />
        </div>
    </a-card>
</template>

<script>
    import { mapGetters } from 'vuex'
    import { Message, MessageBox } from 'element-ui'
    import {getInfo,add,update,del,getList} from '@/api/${meta.name}'
    import Axios from  'axios'

    import StandardTable from '../../components/table/StandardTable'
    const columns = [
        {
            title: '规则编号',
            dataIndex: 'no'
        },
        {
            title: '描述',
            dataIndex: 'description'
        },
        {
            title: '服务调用次数',
            dataIndex: 'callNo',
            sorter: true,
            needTotal: true,
            customRender: (text) => text + ' 次'
        },
        {
            title: '状态',
            dataIndex: 'status',
            needTotal: true
        },
        {
            title: '更新时间',
            dataIndex: 'updatedAt',
            sorter: true
        }
    ]

    const dataSource = []


    export default {
        name: 'QueryList',
        components: {StandardTable},
        data () {
            return {
                advanced: true,
                columns: columns,
                dataSource: dataSource,
                selectedRowKeys: [],
                selectedRows: []
            }
        },
        methods: {
            toggleAdvanced () {
                this.advanced = !this.advanced
            },
            onchange (selectedRowKeys, selectedRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectedRows = selectedRows
            },
            remove () {
                this.dataSource = this.dataSource.filter(item => this.selectedRowKeys.indexOf(item.key) < 0)
                this.selectedRows = this.selectedRows.filter(item => this.selectedRowKeys.indexOf(item.key) < 0)
            },
            addNew () {
                this.dataSource.unshift({
                     key: this.dataSource.length,
                    no: 'NO ' + this.dataSource.length,
                    description: '这是一段描述',
                    callNo: Math.floor(Math.random() * 1000),
                    status: Math.floor(Math.random() * 10) % 4,
                    updatedAt: '2018-07-26'
                })
            },
            handleMenuClick (e) {
                if (e.key === 'delete') {
                    this.remove()
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .search{
        margin-bottom: 54px;
    }
    .fold{
        width: calc(100% - 216px);
        display: inline-block
    }
    .operator{
        margin-bottom: 18px;
    }
    @media screen and (max-width: 900px) {
        .fold {
            width: 100%;
        }
    }
</style>
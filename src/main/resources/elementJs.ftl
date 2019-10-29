import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '${meta.path}/page/query',
        method: 'POST',
        data: params
    })
}

export function getInfo(id) {
    return request({
        url: '${meta.path}/info/'+id,
        method: 'GET',
    })
}


export function update(params) {
    return request({
        url: '${meta.path}/update',
        method: 'POST',
        data: params
    })
}

export function add(params) {
    return request({
        url: '${meta.path}/save',
        method: 'POST',
        data: params
    })
}

export function del(id) {
    return request({
        url: '${meta.path}/delete/'+id,
        method: 'GET',
    })
}





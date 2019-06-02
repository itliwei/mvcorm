import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '${meta.path}/page/query',
        method: 'POST',
        params
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
        params
    })
}

export function add(params) {
    return request({
        url: '${meta.path}/add',
        method: 'POST',
        params
    })
}

export function delete(id) {
    return request({
        url: '${meta.path}/info/'+id,
        method: 'GET',
    })
}





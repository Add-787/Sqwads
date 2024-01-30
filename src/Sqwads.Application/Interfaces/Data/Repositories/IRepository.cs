using Sqwads.Domain.Common;

namespace Sqwads.Application.Interfaces.Data.Repositories;

public interface IRepository<TEntity> where TEntity : IEntity
{
    Task<TEntity?> GetByIdAsync(int id);

    void Add(TEntity entity);
    void AddRange(IEnumerable<TEntity> entities);

    void Remove(TEntity entity);
    void RemoveRange(IEnumerable<TEntity> entities);

    void StartTracking(TEntity entity);
}
